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
    public Member save(Member newMember) {
        return memberRepository.save(newMember);
    }
}
