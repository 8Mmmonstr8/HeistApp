package ua.hubanov.heist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hubanov.heist.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill findByName(String name);
}
