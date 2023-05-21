package gachon.mudang.member.service;

import gachon.mudang.exception.MemberEmailDuplicateException;
import gachon.mudang.exception.MemberNotFoundException;
import gachon.mudang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberUpdateRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long join(Member member) {
        existMemberCheck(member.getEmail());
        member.encryptPassword(passwordEncoder);
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional
    public void update(Long memberId, MemberUpdateRequest request) throws IOException {
        Member member = findOne(memberId);
        member.updateNickName(request.getNickName());
//        if(!request.getProfile().isEmpty()){
//            // 수정필요
////            String imageUrl = s3Uploader.getImageUrl(request.getProfile(), ImageStorageFolderName.MEMBER_IMAGE_PATH);
//            member.updateProfile(imageUrl);
//        }
    }

    @Override
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(() -> {throw new MemberNotFoundException();});
    }

    @Override
    public Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()->{throw new MemberNotFoundException();});
    }

    @Override
    public void existMemberCheck(String email){
        if(memberRepository.findByEmail(email).isPresent()) throw new MemberEmailDuplicateException();
    }

}
