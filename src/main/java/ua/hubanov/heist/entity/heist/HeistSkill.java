package ua.hubanov.heist.entity.heist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.hubanov.heist.entity.Skill;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "HeistSkill")
@Table(name = "heist_skill")
public class HeistSkill {

    @EmbeddedId
    private HeistSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("heistId")
    private Heist heist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    private Skill skill;

    @Column(name = "level", columnDefinition = "varchar(10) default '*'")
    private String level;

    @Column(name = "members")
    private int members;

    public HeistSkill() {
    }

    public HeistSkill(Heist heist, Skill skill, String level, int members) {
        this.id = new HeistSkillId(heist.getId(), skill.getId());
        this.heist = heist;
        this.skill = skill;
        this.level = level;
        this.members = members;
    }
}
