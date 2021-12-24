package ua.hubanov.heist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hubanov.heist.entity.heist.Heist;

import java.util.Optional;

public interface HeistRepository extends JpaRepository<Heist, Long> {

    Optional<Heist> findByName(String name);
}
