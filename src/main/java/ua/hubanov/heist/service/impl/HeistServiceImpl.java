package ua.hubanov.heist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hubanov.heist.dto.heist.HeistDTO;
import ua.hubanov.heist.entity.heist.Heist;
import ua.hubanov.heist.exception.HeistAlreadyExistsException;
import ua.hubanov.heist.mapper.HeistMapper;
import ua.hubanov.heist.repository.HeistRepository;
import ua.hubanov.heist.service.HeistService;
import ua.hubanov.heist.service.SkillService;

import javax.transaction.Transactional;

@Service
public class HeistServiceImpl implements HeistService {

    @Autowired
    HeistRepository heistRepository;

    @Autowired
    HeistMapper heistMapper;

    @Autowired
    SkillService skillService;

    @Override
    @Transactional
    public Long createHeist(HeistDTO heistDTO) {
        checkHeistForExistence(heistDTO);
        Heist savedHeist = heistRepository.save(heistMapper.toEntity(heistDTO));
        skillService.addNewSkillsToHeist(savedHeist, heistDTO.getSkills());
        return savedHeist.getId();
    }

    private void checkHeistForExistence(HeistDTO heistDTO) {
        if (heistRepository.findByName(heistDTO.getName()).isPresent()) {
            throw new HeistAlreadyExistsException("Heist already exists with name: " + heistDTO.getName());
        }
    }
}
