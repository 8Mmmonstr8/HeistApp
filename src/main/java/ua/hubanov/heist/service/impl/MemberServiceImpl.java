package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.dto.member.MemberDTO;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.entity.member.Member;
import ua.hubanov.heist.exception.MemberAlreadyExistsException;
import ua.hubanov.heist.exception.MemberNotFoundException;
import ua.hubanov.heist.mapper.MemberMapper;
import ua.hubanov.heist.repository.MemberRepository;
import ua.hubanov.heist.service.MemberService;
import ua.hubanov.heist.service.SkillService;

import javax.transaction.Transactional;
import java.util.Comparator;

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
                .orElseThrow(() -> new MemberNotFoundException(String.format("Member with id: %s not exist", memberId)));
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

    @Override
    @Transactional
    public void deleteMemberSkill(Long memberId, String skillName) {
        Member member = findById(memberId);
        Skill skill = skillService.findByName(skillName);
        member.removeSkill(skill);

        if (skill.equals(member.getMainSkill())) {
            if (member.getSkills().size() > 1) {
                Skill newMainSkill = member.getSkills().stream()
                        .max(Comparator.comparingInt(s -> s.getLevel().length()))
                        .get().getSkill();
                member.setMainSkill(newMainSkill);
            } else {
                member.setMainSkill(null);
            }
        }
    }

    private void checkMemberForExistence(MemberDTO memberDTO) {
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new MemberAlreadyExistsException("Member already exists with Email: " + memberDTO.getEmail());
        }
    }
}
