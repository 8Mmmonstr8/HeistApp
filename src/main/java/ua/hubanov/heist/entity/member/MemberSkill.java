package ua.hubanov.heist.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.hubanov.heist.entity.Skill;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "MemberSkill")
@Table(name = "member_skill")
@JsonIgnoreProperties(value = {"id", "member"})
public class MemberSkill {

    @EmbeddedId
    private MemberSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    private Skill skill;

    @Column(name = "level", columnDefinition = "varchar(10) default '*'")
    private String level;

    private MemberSkill() {
    }

    public MemberSkill(Member member, Skill skill, String level) {
        this.id = new MemberSkillId(member.getId(), skill.getId());
        this.member = member;
        this.skill = skill;
        this.level = level;
    }
}
