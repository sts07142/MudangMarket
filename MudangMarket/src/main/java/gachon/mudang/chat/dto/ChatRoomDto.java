package gachon.mudang.chat.dto;

import gachon.mudang.chat.domain.ChatRoom;
import lombok.Getter;
import gachon.mudang.member.domain.Member;
import gachon.mudang.product.domain.Product;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoomDto {

    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom toEntity(Product product, Member buyer){
        return ChatRoom.builder()
                .buyer(buyer)
                .product(product)
                .build();
    }
}
