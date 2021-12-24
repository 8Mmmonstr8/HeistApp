package ua.hubanov.heist.service;

import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.dto.heist.HeistSkillDTO;
import ua.hubanov.heist.dto.member.MemberSkillDTO;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.entity.heist.Heist;
import ua.hubanov.heist.entity.member.Member;

import java.util.List;

public interface SkillService {

    Skill saveOrReturn(String name);

    Member addNewSkillsToMember(Member member, List<MemberSkillDTO> memberSkills, String mainSkill);

    Member updateMemberSkills(Member member, SkillsDTO newSkills);

    Skill findByName(String skillName);

    Heist addNewSkillsToHeist(Heist heist, List<HeistSkillDTO> skills);
}
