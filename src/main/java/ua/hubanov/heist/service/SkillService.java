package ua.hubanov.heist.service;

import ua.hubanov.heist.entity.Skill;

public interface SkillService {

    Skill saveOrReturn(String name);
}
