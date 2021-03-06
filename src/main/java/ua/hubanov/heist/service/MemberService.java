package ua.hubanov.heist.service;

import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.entity.Member;

public interface MemberService {

    Member findById(Long memberId);

    Long createMember(MemberDTO newMember);

    Long updateMemberSkills(Long memberId, SkillsDTO newSkills);

    void deleteMemberSkill(Long memberId, String skillName);
}
