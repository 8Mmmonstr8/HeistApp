package ua.hubanov.heist.dto.heist;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class HeistSkillDTO {

    private String name;

    @Pattern(regexp = "^\\*{1,10}$", message = "Should be only asterisk from 1 to 10")
    private String level;

    @Min(1)
    private int members;
}
