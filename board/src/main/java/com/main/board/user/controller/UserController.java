package com.main.board.user.controller;

import com.main.board.user.DTO.UserDTO;
import com.main.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/users")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO.SignUpResponse> signup(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(userService.signUp(userDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
