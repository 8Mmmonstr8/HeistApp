package ua.hubanov.heist.service;

import ua.hubanov.heist.dto.heist.HeistDTO;

public interface HeistService {

    Long createHeist(HeistDTO newHeist);
}
