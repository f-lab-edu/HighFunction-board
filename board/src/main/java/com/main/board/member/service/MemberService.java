package com.main.board.member.service;

import com.main.board.member.DTO.MemberDTO;
import com.main.board.member.DTO.SignUpResponse;
import com.main.board.member.Member;
import com.main.board.member.repository.MemberRepository;
import com.main.board.util.Bcrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final Bcrypt bcrypt;


    @Transactional
    public SignUpResponse signUp(MemberDTO memberDTO) {
        String encryptPwd = bcrypt.encrypt(memberDTO.getPassword());
        Member entity = memberDTO.toMemberEntity(encryptPwd);
        memberRepository.save(entity);
        return new SignUpResponse(entity);
    }

}
