package ua.hubanov.heist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.hubanov.heist.dto.member.MemberSkillDTO;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.entity.member.MemberSkill;

@Mapper(componentModel = "spring")
public abstract class SkillMapper {

    @Mapping(target = "name", source = "skill", qualifiedByName = "mapSkillToName")
    public abstract MemberSkillDTO toDto(MemberSkill memberSkill);

    @Named("mapSkillToName")
    String mapSkillToName(Skill skill) {
        return skill.getName();
    }
}
