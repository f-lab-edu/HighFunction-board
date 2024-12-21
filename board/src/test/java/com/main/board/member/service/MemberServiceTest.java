package com.main.board.member.service;

import com.main.board.member.DTO.SignupRequest;
import com.main.board.member.repository.MemberRepository;
import com.main.board.util.BcryptEncoding;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private BcryptEncoding bcryptEncoding;

    @Mock
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        // given
        String rawPwd = "1234";
        String encodingPwd = bcryptEncoding.encrypt(rawPwd);
        SignupRequest signupRequest = new SignupRequest("test", "1234", "김현성", LocalDate.now());

        // when
        memberService.signUp(signupRequest);
        String actual = String.valueOf(memberRepository.findMemberById("test"));
        // then
        assertEquals(encodingPwd, bcryptEncoding.encrypt(rawPwd));
        assertThat(actual.getId()).isEqualTo(signupRequest);

    }
}