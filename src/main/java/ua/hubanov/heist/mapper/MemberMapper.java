package ua.hubanov.heist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.dto.MemberSkillDTO;
import ua.hubanov.heist.entity.Member;
import ua.hubanov.heist.entity.MemberSkill;
import ua.hubanov.heist.entity.Skill;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    @Autowired
    SkillMapper skillMapper;

    @Mappings({
            @Mapping(target = "mainSkill", ignore = true),
            @Mapping(target = "skills", ignore = true)
    })
    public abstract Member toEntity(MemberDTO memberDTO);

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
