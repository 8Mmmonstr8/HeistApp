package ua.hubanov.heist.dto.member;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class MemberSkillDTO {

    private String name;

    @Pattern(regexp = "^\\*{1,10}$", message = "Should be only asterisk from 1 to 10")
    private String level;

    public MemberSkillDTO(String name, String level) {
        this.name = name;
        this.level = level;
    }
}
