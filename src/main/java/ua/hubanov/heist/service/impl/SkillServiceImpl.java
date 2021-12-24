package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.dto.heist.HeistSkillDTO;
import ua.hubanov.heist.dto.member.MemberSkillDTO;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.entity.heist.Heist;
import ua.hubanov.heist.entity.member.Member;
import ua.hubanov.heist.entity.member.MemberSkill;
import ua.hubanov.heist.exception.SkillException;
import ua.hubanov.heist.exception.SkillNotFoundException;
import ua.hubanov.heist.repository.SkillRepository;
import ua.hubanov.heist.service.SkillService;

import java.util.*;
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
    public Skill findByName(String skillName) {
        return skillRepository.findByName(skillName)
                .orElseThrow(() -> new SkillNotFoundException(
                        String.format("Skill with name '%s' is not exists", skillName)));
    }

    @Override
    public Member addNewSkillsToMember(Member member, List<MemberSkillDTO> newMemberSkills, String mainSkill) {
        checkSkills(newMemberSkills);

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

    private void checkSkills(List<MemberSkillDTO> newMemberSkills) {
        List<String> skillNames = newMemberSkills.stream()
                .map(MemberSkillDTO::getName)
                .collect(Collectors.toList());
        skillNames.stream()
                .filter(name -> Collections.frequency(skillNames, name) > 1)
                .findAny()
                .ifPresent(name -> {
                    throw new SkillException(String.format("Skill with name: '%s' is duplicated in array", name));
                });
    }

    @Override
    public Heist addNewSkillsToHeist(Heist heist, List<HeistSkillDTO> skills) {
        checkHeistSkills(skills);

        List<String> heistSkillsNames = heist.getSkills().stream()
                .map(heistSkill -> heistSkill.getSkill().getName())
                .collect(Collectors.toList());

        skills.forEach(heistSkillDTO -> {
            Skill skill = saveOrReturn(heistSkillDTO.getName().toLowerCase());
            if (!heistSkillsNames.contains(skill.getName())) {
                heist.addSkill(skill, heistSkillDTO.getLevel(), heistSkillDTO.getMembers());
            }
        });

        return heist;
    }

    private void checkHeistSkills(List<HeistSkillDTO> skills) {
//        List<String> skillNames = skills.stream()
//                .map(HeistSkillDTO::getName)
//                .collect(Collectors.toList());
//        skillNames.stream()
//                .filter(name -> Collections.frequency(skillNames, name) > 1)
//                .findAny()
//                .ifPresent(name -> {
//                    List<String> levels = new ArrayList<>();
//                    skills.stream()
//                            .filter(skill -> skill.getName().equalsIgnoreCase(name))
//                            .peek(skill -> levels.add(skill.getLevel()))
//                            .filter(skill -> Collections.frequency(levels, skill.getLevel()) > 1)
//                            .findAny()
//                            .ifPresent(skill -> {
//                                throw new SkillException(String.format(
//                                        "Skill with name: '%s' and level: '%s' is duplicated in array",
//                                        skill.getName(), skill.getLevel()));
//                            });
//                });

//        Map<String, List<String>> skillsWithLevelsMap = new HashMap<>();
//
//        for (HeistSkillDTO skill : skills) {
//            if (!skillsWithLevelsMap.containsKey(skill.getName())) {
//                skillsWithLevelsMap.put(skill.getName(),
//                        skills.stream()
//                                .filter(s -> s.getName().equalsIgnoreCase(skill.getName()))
//                                .map(HeistSkillDTO::getLevel)
//                                .collect(Collectors.toList()));
//            }
//        }
//
//        skillsWithLevelsMap.forEach((name, levels) -> levels.stream()
//                .filter(x -> Collections.frequency(levels, x) > 1)
//                .findAny()
//                .ifPresent(level -> {
//                    throw new SkillException(String.format(
//                                        "Skill with name: '%s' and level: '%s' is duplicated in array",
//                                        name, level));
//                    })
//
//        );
    }
}
