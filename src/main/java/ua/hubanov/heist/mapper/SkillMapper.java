package ua.hubanov.heist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.hubanov.heist.dto.MemberSkillDTO;
import ua.hubanov.heist.entity.MemberSkill;
import ua.hubanov.heist.entity.Skill;

@Mapper(componentModel = "spring")
public abstract class SkillMapper {

    @Mapping(target = "name", source = "skill", qualifiedByName = "mapSkillToName")
    public abstract MemberSkillDTO toDto(MemberSkill memberSkill);

    @Named("mapSkillToName")
    String mapSkillToName(Skill skill) {
        return skill.getName();
    }
}
