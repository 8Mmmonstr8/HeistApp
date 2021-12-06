package ua.hubanov.heist.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.hubanov.heist.entity.enums.Sex;
import ua.hubanov.heist.entity.enums.Status;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "member")
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "member")
    private List<MemberSkill> skills;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill mainSkill;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Member() {
    }

    public Member(Long id, String name, Sex sex, String email, List<MemberSkill> skills, Skill mainSkill, Status status) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.skills = skills;
        this.mainSkill = mainSkill;
        this.status = status;
    }
}
