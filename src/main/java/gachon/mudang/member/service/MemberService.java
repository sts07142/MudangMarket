package gachon.mudang.member.service;

import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberUpdateRequest;

import java.io.IOException;

public interface MemberService {
    Long join(Member member);
    void update(Long memberId, MemberUpdateRequest request) throws IOException;
    Member findOne(Long memberId);
    Member findMember(String email);
    void existMemberCheck(String email);
}
