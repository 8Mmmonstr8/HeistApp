package ua.hubanov.heist.dto;

import lombok.Data;
import ua.hubanov.heist.entity.enums.Sex;
import ua.hubanov.heist.entity.enums.Status;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MemberDTO {

    private Long id;

    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    private Sex sex;

    @Email
    private String email;

    @Valid
    private List<MemberSkillDTO> skills;

    private String mainSkill;

    private Status status;
}
