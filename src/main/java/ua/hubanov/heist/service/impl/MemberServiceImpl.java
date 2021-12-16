package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.exception.MemberException;
import ua.hubanov.heist.exception.SkillException;
import ua.hubanov.heist.mapper.MemberMapper;
import ua.hubanov.heist.repository.MemberRepository;
import ua.hubanov.heist.service.MemberService;
import ua.hubanov.heist.service.SkillService;

import javax.transaction.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SkillService skillService;

    @Autowired
    MemberMapper memberMapper;

    @Override
    @Transactional
    public Long createMember(MemberDTO memberDTO) {
        checkMemberForExistence(memberDTO);

        Member newMember = memberMapper.toEntity(memberDTO);

        newMember.setMainSkill(skillService.saveOrReturn(
                memberDTO.getSkills().stream()
                        .filter(memberSkill -> memberSkill.getName().equalsIgnoreCase(memberDTO.getMainSkill()))
                        .findFirst().orElseThrow(() -> new SkillException("Main skill must be in skill array"))
                        .getName().toLowerCase()));

        Member savedMember = memberRepository.save(newMember);

        memberDTO.getSkills().forEach(memberSkillDTO -> {
            Skill skill = skillService.saveOrReturn(memberSkillDTO.getName().toLowerCase());
            savedMember.addSkill(skill, memberSkillDTO.getLevel());
        });

        return savedMember.getId();
    }

    private void checkMemberForExistence(MemberDTO memberDTO) {
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new MemberException("Member already exists with Email: " + memberDTO.getEmail());
        }
    }
}
