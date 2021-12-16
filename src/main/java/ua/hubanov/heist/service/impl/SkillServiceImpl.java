package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.dto.MemberSkillDTO;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.entity.MemberSkill;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.exception.SkillException;
import ua.hubanov.heist.repository.SkillRepository;
import ua.hubanov.heist.service.SkillService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public Skill saveOrReturn(String name) {
        return skillRepository.findByName(name)
                .orElseGet(() -> skillRepository.save(new Skill(name)));
    }

    @Override
    public Member addNewSkillsToMember(Member member, List<MemberSkillDTO> newMemberSkills, String mainSkill) {
        List<String> memberSkillsNames = member.getSkills().stream()
                        .map(memberSkill -> memberSkill.getSkill().getName())
                                .collect(Collectors.toList());

        member.setMainSkill(saveOrReturn(
                newMemberSkills.stream()
                        .filter(memberSkill -> memberSkill.getName().equalsIgnoreCase(mainSkill))
                        .findFirst().orElseThrow(() -> new SkillException("Main skill must be in skill array"))
                        .getName().toLowerCase()));

        newMemberSkills.forEach(memberSkillDTO -> {
            Skill skill = saveOrReturn(memberSkillDTO.getName().toLowerCase());
            if (!memberSkillsNames.contains(skill.getName())) {
                member.addSkill(skill, memberSkillDTO.getLevel());
            }
        });

        return member;
    }

    @Override
    public Member updateMemberSkills(Member member, SkillsDTO newSkills) {
        updateAlreadyExistedSkills(member, newSkills);
        removeAllMemberSkillsWhichIsNotInNewSkills(member, newSkills);
        addNewSkillsToMember(member, newSkills.getSkills(), newSkills.getMainSkill());
        return member;
    }

    private void updateAlreadyExistedSkills(Member member, SkillsDTO newSkills) {
        for (MemberSkill memberSkill : member.getSkills()) {
            for (MemberSkillDTO memberSkillDTO : newSkills.getSkills()) {
                if (memberSkill.getSkill().getName().equalsIgnoreCase(memberSkillDTO.getName())) {
                    memberSkill.setLevel(memberSkillDTO.getLevel());
                }
            }
        }
    }

    private void removeAllMemberSkillsWhichIsNotInNewSkills(Member member, SkillsDTO newSkills) {
        List<String> newSkillNames = newSkills.getSkills().stream()
                .map(memberSkillDTO -> memberSkillDTO.getName().toLowerCase())
                .collect(Collectors.toList());

        List<Skill> skillsToDelete = member.getSkills().stream()
                .map(MemberSkill::getSkill)
                .filter(skill -> !newSkillNames.contains(skill.getName()))
                .collect(Collectors.toList());

        member.removeSkills(skillsToDelete);
    }
}