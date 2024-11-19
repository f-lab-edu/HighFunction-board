package com.main.board.security;

import com.main.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        boolean user = userRepository.findUserById(user_id);
        if(!user) {
            throw new UsernameNotFoundException("해당하는 사용자가 없습니다.");
        }
        return new PrincipalDetails(user);
    }
}
