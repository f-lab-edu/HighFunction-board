package com.main.board.user.repository;

import com.main.board.user.DTO.UserDTO;
import com.main.board.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
    void save(User user);
    boolean findUserById(String user_id);
}
