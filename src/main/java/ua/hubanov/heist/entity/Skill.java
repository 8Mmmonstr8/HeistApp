package ua.hubanov.heist.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.hubanov.heist.entity.heist.HeistSkill;
import ua.hubanov.heist.entity.member.MemberSkill;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "skill")
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(
            mappedBy = "skill",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MemberSkill> members = new ArrayList<>();

    @OneToMany(
            mappedBy = "skill",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<HeistSkill> heists = new ArrayList<>();

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }
}
