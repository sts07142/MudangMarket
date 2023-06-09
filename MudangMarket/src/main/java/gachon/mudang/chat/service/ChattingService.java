package gachon.mudang.chat.service;

import gachon.mudang.chat.dto.ChatDto;
import gachon.mudang.chat.domain.Chat;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.member.domain.Member;

import java.util.List;

public interface ChattingService {

    // Method signature for finding all chat rooms associated with the given member email.
    List<ChatRoom> findChatRoomByMember(String email);

    // Method signature for finding a chat room by given product id and buyer id.
    ChatRoom findChatRoomByBuyer(Long productId, Long buyerId);

    // Method signature for saving a chat into the database. It returns the saved chat's id.
    Long saveChat(ChatDto chatDto);

    // Method signature for finding all chat rooms by given product id.
    List<ChatRoom> findByProductId(Long productId);

    // Method signature for finding all chats in a chat room by given room id.
    List<Chat> findChatList(Long roomId);

    // Method signature for finding a chat room by room name and member.
    // If there is no such chat room, it should be created and returned.
    ChatRoom findChatRoomByName(Member member, String roomName, Long productId);
}

