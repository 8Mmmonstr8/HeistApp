package ua.hubanov.heist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.hubanov.heist.dto.heist.HeistDTO;
import ua.hubanov.heist.service.HeistService;

import javax.validation.Valid;

@RestController
public class HeistController {

    @Autowired
    HeistService heistService;

    @PostMapping("/heist")
    public ResponseEntity<String> createMember(@Valid @RequestBody HeistDTO newHeist) {
        return new ResponseEntity<>("Header: localhost:8080/heist/" + heistService.createHeist(newHeist),
                HttpStatus.CREATED);
    }
}
