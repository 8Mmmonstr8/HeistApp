package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.entity.Skill;
import ua.hubanov.heist.repository.SkillRepository;
import ua.hubanov.heist.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public Skill saveOrReturn(String name) {
        return skillRepository.findByName(name)
                .orElseGet(() -> skillRepository.save(new Skill(name)));
    }
}
