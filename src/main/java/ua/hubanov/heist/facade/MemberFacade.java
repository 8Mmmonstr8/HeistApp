package ua.hubanov.heist.facade;

import ua.hubanov.heist.dto.MemberDTO;

public interface MemberFacade {

    MemberDTO save(MemberDTO newMember);
}
