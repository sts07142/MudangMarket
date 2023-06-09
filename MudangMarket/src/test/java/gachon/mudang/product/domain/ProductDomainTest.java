package gachon.mudang.product.domain;

import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberJoinRequest;
import gachon.mudang.member.repository.MemberRepository;
import gachon.mudang.product.dto.ProductRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductDomainTest {
    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Test
    void 상품_생성(){
        // 회원 생성
        Member seller = em.find(Member.class, createMember());
        // DTO -> Entity 변환
        Product product = ProductRegisterRequest.builder()
                .title("상품명")
                .price(2000)
                .category(ProductCategory.GAME_HOBBIES.getValue())
                .content("상품 정보").build().toProductEntity();
        product.addProduct(seller);
        em.persist(product);
        Long productId = product.getId();
        Product findProduct = em.find(Product.class, productId);
        assertThat(findProduct).isEqualTo(product);
        assertThat(findProduct.getSeller()).isEqualTo(seller);
    }
    Long createMember(){
        Member member = MemberJoinRequest.builder()
                .email("test@naver.com")
                .password("123")
                .nickname("닉네임")
                .phone("휴대폰")
                .name("이름")
                .build().toMemberEntity();
        em.persist(member);
        return member.getId();
    }
}
