package ua.hubanov.heist.dto.heist;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class HeistDTO {

    private Long id;

    @Size(min = 2, message = "Heist name should have at least 2 characters")
    private String name;

    @Size(min = 2, message = "Heist name should have at least 2 characters")
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
    private Date endTime;

    @Valid
    private List<HeistSkillDTO> skills = new ArrayList<>();
}
