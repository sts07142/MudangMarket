package gachon.mudang.chat.service;

import gachon.mudang.chat.dto.ChatDto;
import gachon.mudang.chat.domain.Chat;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.member.domain.Member;

import java.util.List;

public interface ChattingService {
    List<ChatRoom> findChatRoomByMember(String email);
    ChatRoom findChatRoomByBuyer(Long productId, Long buyerId);
    Long saveChat(ChatDto chatDto);
    List<ChatRoom> findByProductId(Long productId);
    List<Chat> findChatList(Long roomId);
    ChatRoom findChatRoomByName(Member member, String roomName, Long productId);

}
