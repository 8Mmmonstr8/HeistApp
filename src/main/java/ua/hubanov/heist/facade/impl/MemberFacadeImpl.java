package ua.hubanov.heist.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.dto.MemberSkillDTO;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.entity.MemberSkill;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.facade.MemberFacade;
import ua.hubanov.heist.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberFacadeImpl implements MemberFacade {

    @Autowired
    MemberService memberService;

    @Override
    public MemberDTO save(MemberDTO newMember) {
        Member member = toEntity(newMember);
        return toDto(memberService.save(member));
    }

    private Member toEntity(MemberDTO memberDTO) {
        Member member = new Member();
        List<MemberSkill> memberSkills = memberDTO.getSkills()
                .stream()
                .map(x -> new MemberSkill(member, new Skill(x.getName()), x.getLevel()))
                .collect(Collectors.toList());
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setSex(memberDTO.getSex());
        member.setStatus(memberDTO.getStatus());
        member.setSkills(memberSkills);
        member.setMainSkill(memberSkills
                .stream()
                .filter(x -> x.getSkill().getName().equals(memberDTO.getMainSkill()))
                .findFirst().get().getSkill());
        return member;
    }

    private MemberDTO toDto(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(memberDTO.getName());
        memberDTO.setEmail(memberDTO.getEmail());
        memberDTO.setSex(member.getSex());
        memberDTO.setStatus(member.getStatus());
        memberDTO.setMainSkill(member.getMainSkill().getName());
        List<MemberSkillDTO> memberSkills = member.getSkills().stream()
                .map(skill -> new MemberSkillDTO(skill.getSkill().getName(), skill.getLevel()))
                .collect(Collectors.toList());
        memberDTO.setSkills(memberSkills);
        return memberDTO;
    }
}
