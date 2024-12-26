package com.main.board.member.service;

import com.main.board.member.DTO.SignupRequest;
import com.main.board.member.DTO.SignUpResponse;
import com.main.board.member.Member;
import com.main.board.member.repository.MemberRepository;
import com.main.board.util.BcryptEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BcryptEncoder bcryptEncoder;


    @Transactional
    public SignUpResponse signUp(SignupRequest signupRequest) {
        String encryptPwd = bcryptEncoder.encrypt(signupRequest.getRawPassword());
        Member entity = signupRequest.toMemberEntity(encryptPwd);
        memberRepository.save(entity);
        return new SignUpResponse(entity);
    }

}
