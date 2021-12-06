package ua.hubanov.heist.facade.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.hubanov.heist.dto.MemberDTO;
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

    @Autowired
    ModelMapper modelMapper;

    @Override
    public MemberDTO save(MemberDTO newMember) {
//        Member member = modelMapper.map(newMember, Member.class);
        Member member = toEntity(newMember);
        return modelMapper.map(memberService.save(member), MemberDTO.class);
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
}
