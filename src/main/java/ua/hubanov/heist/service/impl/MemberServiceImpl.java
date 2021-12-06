package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.entity.MemberSkill;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.repository.MemberRepository;
import ua.hubanov.heist.repository.SkillRepository;
import ua.hubanov.heist.service.MemberService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SkillRepository skillRepository;

    @Override
    @Transactional
    public Member save(Member newMember) {
        List<Skill> skillList = new ArrayList<>();
        for (MemberSkill x : newMember.getSkills()) {
            skillList.add(skillRepository.save(x.getSkill()));
        }
        Member savedMember = memberRepository.save(newMember);

        List<MemberSkill> newMemberSkills = new ArrayList<>();
        for (Skill skill : skillList) {
            newMemberSkills.add(new MemberSkill(savedMember, skill,
                    newMember.getSkills().stream()
                            .filter(x -> x.getSkill().getName().equals(skill.getName()))
                            .findFirst().get().getLevel()));
        }
        savedMember.setSkills(newMemberSkills);
        for (Skill x : skillList) {
            x.setMembers(newMemberSkills);
            skillRepository.save(x);
        }

        return memberRepository.save(savedMember);
    }
}
