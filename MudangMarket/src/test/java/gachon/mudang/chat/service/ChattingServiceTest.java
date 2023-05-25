package gachon.mudang.chat.service;

import gachon.mudang.chat.dto.ChatDto;
import gachon.mudang.product.domain.ProductCategory;
import gachon.mudang.chat.domain.Chat;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.chat.domain.ChatType;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberJoinRequest;
import gachon.mudang.member.service.MemberService;
import gachon.mudang.product.domain.Product;
import gachon.mudang.product.dto.ProductRegisterRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
class ChattingServiceTest {

    @Autowired MemberService memberService;
    @Autowired ChattingService chattingService;
    @Autowired EntityManager em;


    @Test
    @Transactional
    void 채팅_생성(){
        Member member = em.find(Member.class, createMember());
        ChatRoom room = chattingService.findChatRoomByName(member, "", createProduct());
        Long chatId = chattingService.saveChat(ChatDto.builder()
                .roomId(room.getId())
                .nickname(member.getNickName())
                .profile(member.getProfile())
                .type(ChatType.MESSAGE)
                .content("메시지").build());
        Chat newChat = em.find(Chat.class, chatId);
        newChat.getChatRoom().getChats().add(newChat);
        List<ChatRoom> chatList = chattingService.findChatRoomByMember(member.getEmail());
        List<Chat> chats = chatList.stream().map(i -> i.getLastChat()).collect(Collectors.toList());
        Assertions.assertThat(room.getChats().size()).isEqualTo(1);
        Assertions.assertThat(room.getLastChat().getContent()).isEqualTo("메시지");
    }

    @Test
    @Transactional
    void findChatRoomByMember(){
        List<ChatRoom> chatRoomByMember = chattingService.findChatRoomByMember("test@naver.com");
//        chatRoomByMember.get(0).getProduct().getId();
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
        // 연관관계 편의 메서드 실행
        product.addProduct(seller);
        // 상품 DB 저장
        em.persist(product);
        return product.getId();
    }

}