package ua.hubanov.heist.service;

import ua.hubanov.heist.dto.MemberSkillDTO;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.entity.Skill;

import java.util.List;

public interface SkillService {

    Skill saveOrReturn(String name);

    Member addNewSkillsToMember(Member member, List<MemberSkillDTO> memberSkills, String mainSkill);

    Member updateMemberSkills(Member member, SkillsDTO newSkills);
}
