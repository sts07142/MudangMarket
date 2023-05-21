package gachon.mudang.interest.service;

import gachon.mudang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import gachon.mudang.exception.DuplicateInterestExistsException;
import gachon.mudang.exception.InterestNotFoundException;
import gachon.mudang.interest.domain.Interest;
import gachon.mudang.interest.repository.InterestRepository;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{

    private final InterestRepository interestRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Long addInterestList(String email, Long productId) {
        Member member = memberRepository.findByEmail(email).get();
        checkInterestDuplicate(member.getId(), productId);
        Interest interest = Interest.builder()
                .member(member)
                .product(productRepository.findById(productId).get()).build();
        interestRepository.save(interest);
        return interest.getId();
    }

    @Transactional
    @Override
    public void deleteInterestByProductList(String email, Long productId) {
        Member member = memberRepository.findByEmail(email).get();
        Interest interest = interestRepository.findByMemberIdAndProductId(member.getId(), productId)
                .orElseThrow(() -> {throw new InterestNotFoundException();});
        interest.reduceProductInterestCount();
        interestRepository.delete(interest);
    }

    @Override
    public void checkInterestDuplicate(Long memberId, Long productId){
        if(interestRepository.findByMemberIdAndProductId(memberId, productId).isPresent())
            throw new DuplicateInterestExistsException();
    }

}
