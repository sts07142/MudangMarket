package gachon.mudang.product.domain;

import gachon.mudang.product.service.ProductServiceImpl;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberJoinRequest;
import gachon.mudang.member.service.MemberService;
import gachon.mudang.product.dto.ProductRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.*;
import java.util.UUID;

@SpringBootTest
@Transactional
class ProductDateHandlerTest {
    @Autowired
    ProductServiceImpl productService;
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
    void 시간_치환_함수_테스트(){
        Product product = em.find(Product.class, createProduct());
        System.out.println(replaceProductDate(product.getDate().toLocalDateTime()));
    }
    public String replaceProductDate(LocalDateTime productDate){
        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"));
        Duration duration = Duration.between(productDate, now);
        Period period = Period.between(productDate.toLocalDate(), now.toLocalDate());
        if(duration.toHours()<1){ return duration.toMinutes() + " 분 전";}
        if(duration.toDays()<1){ return duration.toHours() + " 시간 전";}
        if(period.getMonths()<1){ return period.getDays() + " 일 전";}
        if (period.getYears()<1) { return period.getMonths() + " 달 전";}
        return period.getYears() + " 년 전";
    }
}