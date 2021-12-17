package ua.hubanov.heist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hubanov.heist.dto.MemberDTO;
import ua.hubanov.heist.dto.SkillsDTO;
import ua.hubanov.heist.service.MemberService;

import javax.validation.Valid;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<String> createMember(@Valid @RequestBody MemberDTO newMember) {
        return new ResponseEntity<>("Header: localhost:8080/member/" + memberService.createMember(newMember),
                HttpStatus.CREATED);
    }

    @PutMapping("/member/{memberId}/skills")
    public ResponseEntity<String> createMember(@PathVariable Long memberId, @Valid @RequestBody SkillsDTO skills) {
        return new ResponseEntity<>(String.format("Header: localhost:8080/member/%s/skills",
                memberService.updateMemberSkills(memberId, skills)), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/member/{memberId}/skills/{skillName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMemberSkill(@PathVariable Long memberId, @PathVariable String skillName) {
        memberService.deleteMemberSkill(memberId, skillName);
    }
}
