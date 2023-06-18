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

    // Represents the WebSocket sessions associated with this chat room.
    // A Set is used to prevent duplicate sessions.
    private Set<WebSocketSession> sessions = new HashSet<>();

    // Method to convert the DTO object to an entity object.
    // This method is typically used when we want to convert incoming DTO into an entity for persistence.
    public ChatRoom toEntity(Product product, Member buyer){
        return ChatRoom.builder()
                .buyer (buyer)
                .product(product)
                .build();
    }
}

