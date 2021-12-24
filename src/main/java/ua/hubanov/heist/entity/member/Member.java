package ua.hubanov.heist.entity.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.entity.enums.Sex;
import ua.hubanov.heist.entity.enums.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
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

    @OneToMany(mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<MemberSkill> skills = new ArrayList<>();

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

    public void removeSkills(List<Skill> skills) {
        skills.forEach(this::removeSkill);
    }

    public void removeSkill(Skill skill) {
        for (Iterator<MemberSkill> iterator = skills.iterator();
             iterator.hasNext(); ) {
            MemberSkill memberSkill = iterator.next();

            if (memberSkill.getMember().equals(this) &&
                    memberSkill.getSkill().equals(skill)) {
                iterator.remove();
                memberSkill.getSkill().getMembers().remove(memberSkill);
                memberSkill.setMember(null);
                memberSkill.setSkill(null);
            }
        }
    }

    public void addSkill(Skill skill, String level) {
        MemberSkill newMemberSkill = new MemberSkill(this, skill, level);
        skills.add(newMemberSkill);
        skill.getMembers().add(newMemberSkill);
    }
}
