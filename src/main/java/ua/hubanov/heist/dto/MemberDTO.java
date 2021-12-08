package ua.hubanov.heist.dto;

import lombok.Data;
import ua.hubanov.heist.entity.enums.Sex;
import ua.hubanov.heist.entity.enums.Status;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MemberDTO {

    private Long id;

    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    private Sex sex;

    @Pattern(regexp = "^(.+)@(.+)$", message = "Email is invalid")
    private String email;

    private List<MemberSkillDTO> skills;

    private String mainSkill;

    private Status status;
}
