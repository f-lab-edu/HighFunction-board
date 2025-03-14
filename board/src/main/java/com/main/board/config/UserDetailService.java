package com.main.board.config;

import com.main.board.member.Member;
import com.main.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security는 해당 객체를 기반으로 인증을 처리한다
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //로그인시 유저를 찾고 유저가 있으면 CustomUserDetails를 반환
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 없습니다."));
        return new CustomUserDetails(member);
    }
}
