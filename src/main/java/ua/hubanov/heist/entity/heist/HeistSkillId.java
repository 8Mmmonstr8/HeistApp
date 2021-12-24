package ua.hubanov.heist.entity.heist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class HeistSkillId implements Serializable {

    @Column(name = "heist_id")
    private Long heistId;

    @Column(name = "skill_id")
    private Long skillId;

    public HeistSkillId() {
    }

    public HeistSkillId(Long heistId, Long skillId) {
        this.heistId = heistId;
        this.skillId = skillId;
    }
}
