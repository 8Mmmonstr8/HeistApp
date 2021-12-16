package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.exception.MemberAlreadyExistsException;
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
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberAlreadyExistsException(String.format("Member with id: %s not exist", memberId)));
    }

    @Override
    @Transactional
    public Long createMember(MemberDTO memberDTO) {
        checkMemberForExistence(memberDTO);
        Member savedMember = memberRepository.save(memberMapper.toEntity(memberDTO));

        skillService.addNewSkillsToMember(savedMember, memberDTO.getSkills(), memberDTO.getMainSkill());

        return savedMember.getId();
    }

    @Override
    @Transactional
    public Long updateMemberSkills(Long memberId, SkillsDTO newSkills) {
        Member member = findById(memberId);

        skillService.updateMemberSkills(member, newSkills);

        return member.getId();
    }

    private void checkMemberForExistence(MemberDTO memberDTO) {
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new MemberAlreadyExistsException("Member already exists with Email: " + memberDTO.getEmail());
        }
    }
}
