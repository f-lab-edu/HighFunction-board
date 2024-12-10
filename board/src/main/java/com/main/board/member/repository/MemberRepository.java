package com.main.board.member.repository;

import com.main.board.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    void save(Member member);
    Optional<Member> findMemberById(String user_id); // DetailsService에서 orElseThrow때문에 Optional사용
}
