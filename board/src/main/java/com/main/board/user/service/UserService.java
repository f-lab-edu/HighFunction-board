package com.main.board.user.service;

import com.main.board.user.DTO.UserDTO;
import com.main.board.user.User;
import com.main.board.user.repository.UserRepository;
import com.main.board.util.Bcrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Bcrypt bcrypt;


    @Transactional
    public UserDTO.SignUpResponse signUp(UserDTO userDTO) {
        String encryptPwd = bcrypt.encrypt(userDTO.getPassword());
        User entity = userDTO.toUserEntity(encryptPwd);
        userRepository.save(entity);
        return new UserDTO.SignUpResponse(entity);
    }

    public UserDTO.LoginResponse login(UserDTO userDTO) {
        Boolean userCheck = userRepository.findUserById(userDTO.getUser_id());

        if(userCheck) {
            User user = userRepository.findByEmail(userDTO.getUser_id());
            if(bcrypt.isMatch(userDTO.getPassword(), user.getPassword())) {
                return new UserDTO.LoginResponse(user);
            }
            else {
                throw new UserNotFoundException("비밀번호가 일치하지 않습니다.");
            }
        }
        else {
            throw new UserNotFoundException("해당하는 사용자가 없습니다.");
        }

        return new UserDTO.LoginResponse(user);
    }
}
