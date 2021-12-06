package ua.hubanov.heist.entity;

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
public class MemberSkillId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "skill_id")
    private Long skillId;

    public MemberSkillId() {
    }

    public MemberSkillId(Long memberId, Long skillId) {
        this.memberId = memberId;
        this.skillId = skillId;
    }
}
