package ua.hubanov.heist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.facade.MemberFacade;

import javax.validation.Valid;

@RestController
public class MemberController {

    @Autowired
    MemberFacade memberFacade;

    @PostMapping("/member")
    public ResponseEntity<String> create(@Valid @RequestBody MemberDTO newMember) {
        return new ResponseEntity<>("Header: localhost:8080/member/" + memberFacade.save(newMember).getId(),
                HttpStatus.CREATED);
    }
}
