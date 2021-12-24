package ua.hubanov.heist.entity.heist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.hubanov.heist.entity.Skill;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "heist")
@Table(name = "heist")
public class Heist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private Date startTime;

    private Date endTime;

    @OneToMany(mappedBy = "heist",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<HeistSkill> skills = new ArrayList<>();

    public Heist() {
    }

    public Heist(Long id, String name, String location, Date startTime, Date endTime, List<HeistSkill> skills) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skills = skills;
    }

    public void addSkill(Skill skill, String level, int members) {
        HeistSkill newHeistSkill = new HeistSkill(this, skill, level, members);
        skills.add(newHeistSkill);
        skill.getHeists().add(newHeistSkill);
    }
}
