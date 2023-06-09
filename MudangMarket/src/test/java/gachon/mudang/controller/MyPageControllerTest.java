package gachon.mudang.controller;

import gachon.mudang.product.domain.ProductCategory;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberJoinRequest;
import gachon.mudang.member.service.MemberService;
import gachon.mudang.product.domain.Product;
import gachon.mudang.product.dto.ProductRegisterRequest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test2@naver.com", password = "123", roles = "USER")
@Transactional
class MyPageControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext context;
    @Autowired
    MemberService memberService;
    @Autowired EntityManager em;
    @Before
    public void setting(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
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
    void 채팅목록_조회() throws Exception{
        System.out.println("MyPageControllerTest.채팅목록_조회");
        mvc.perform(get("/my-page/chat"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}