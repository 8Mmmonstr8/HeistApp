package ua.hubanov.heist.service;

import ua.hubanov.heist.dto.MemberDTO;

public interface MemberService {

    Long createMember(MemberDTO newMember);
}
