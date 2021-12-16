package ua.hubanov.heist.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class SkillsDTO {

    @Valid
    private List<MemberSkillDTO> skills;

    private String mainSkill;
}
