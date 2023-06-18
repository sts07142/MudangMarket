package gachon.mudang.interest.service;

import gachon.mudang.exception.DuplicateInterestExistsException;
import gachon.mudang.product.domain.ProductCategory;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberJoinRequest;
import gachon.mudang.member.service.MemberService;
import gachon.mudang.product.domain.Product;
import gachon.mudang.product.dto.ProductRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InterestServiceImplTest {
    @Autowired
    InterestService interestService;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;
    public Long createMember(){
        Member member = MemberJoinRequest.builder()
                .email(UUID.randomUUID().toString())
                .name("이름")
                .phone("휴대폰")
                .nickname("닉네임")
                .password("비밀번호")
                .build().toMemberEntity();
        return memberService.join(member);
    }
    public Long createProduct(){
        Member seller = memberService.findOne(createMember());
        Product product = ProductRegisterRequest.builder()
                .title("상품명")
                .price(2000)
                .category(ProductCategory.GAME_HOBBIES.getValue())
                .content("상품 정보").build().toProductEntity();
        product.addProduct(seller);
        em.persist(product);
        return product.getId();
    }
    @Test
    @Transactional
    void 하트_누르기_성공() {
        Product product = em.find(Product.class, createProduct());
        Member member = memberService.findOne(createMember());
        interestService.addInterestList(member.getEmail(), product.getId());
        assertThat(product).isIn(member.getProductByInterest());
    }
    @Test
    @Transactional
    void 관심_목록_추가_실패(){
        Member member = memberService.findOne(createMember());
        Product product = em.find(Product.class, createProduct());
        interestService.addInterestList(member.getEmail(), product.getId());
        em.flush();
        assertThrows(DuplicateInterestExistsException.class, ()-> interestService.addInterestList(member.getEmail(), product.getId()));
    }
    @Test
    @Transactional
    void 관심_목록_삭제_성공(){
        Member member = memberService.findOne(createMember());
        Product product = em.find(Product.class, createProduct());
        int before = member.getInterests().size();
        interestService.addInterestList(member.getEmail(), product.getId());
        em.flush();
        interestService.deleteInterestByProductList(member.getEmail(), product.getId());
        int after = em.find(Member.class, member.getId()).getInterests().size();
        assertThat(after).isEqualTo(before);
    }
}