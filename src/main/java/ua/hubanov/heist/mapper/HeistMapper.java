package ua.hubanov.heist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ua.hubanov.heist.dto.heist.HeistDTO;
import ua.hubanov.heist.dto.member.MemberDTO;
import ua.hubanov.heist.dto.member.MemberSkillDTO;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.entity.heist.Heist;
import ua.hubanov.heist.entity.member.Member;
import ua.hubanov.heist.entity.member.MemberSkill;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class HeistMapper {

    @Autowired
    SkillMapper skillMapper;

    @Mapping(target = "skills", ignore = true)
    public abstract Heist toEntity(HeistDTO heistDTO);

    @Mappings({
            @Mapping(target = "mainSkill", source = "mainSkill", qualifiedByName = "mapSkillToName"),
            @Mapping(target = "skills", source = "skills", qualifiedByName = "mapSkills")
    })
    public abstract MemberDTO toDto(Member member);

    @Named("mapSkillToName")
    String mapSkillToName(Skill skill) {
        return skill.getName();
    }

    @Named("mapSkills")
    List<MemberSkillDTO> mapSkills(List<MemberSkill> skills) {
        return skills.stream().map(skillMapper::toDto).collect(Collectors.toList());
    }

}
