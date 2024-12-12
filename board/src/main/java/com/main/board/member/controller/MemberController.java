package com.main.board.member.controller;

import com.main.board.member.DTO.MemberDTO;
import com.main.board.member.DTO.SignUpResponse;
import com.main.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody MemberDTO memberDTO) {
        try {
            return new ResponseEntity<>(memberService.signUp(memberDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
