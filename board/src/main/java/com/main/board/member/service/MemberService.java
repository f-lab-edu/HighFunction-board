package com.main.board.member.service;

import com.main.board.member.DTO.SignupRequest;
import com.main.board.member.Member;
import com.main.board.member.exception.EmailDuplicatedException;
import com.main.board.member.repository.MemberRepository;
import com.main.board.util.PassWordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PassWordEncoder passWordEncoder;


    @Transactional
    public void signUp(SignupRequest signupRequest) {
            if(memberRepository.existsByEmail(signupRequest.getEmail())) {
                throw new EmailDuplicatedException("이미 가입된 이메일입니다.");
            }
            String encryptPwd = passWordEncoder.encode(signupRequest.getRawPassword());
            Member entity = signupRequest.toMemberEntity(encryptPwd);
            memberRepository.save(entity);
    }

}
