package gachon.mudang.chat.service;

import gachon.mudang.chat.dto.ChatDto;
import gachon.mudang.chat.repository.ChatRepository;
import gachon.mudang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import gachon.mudang.chat.domain.Chat;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.chat.repository.ChatRoomRepository;
import gachon.mudang.member.domain.Member;
import gachon.mudang.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService{
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ProductRepository productRepository;

    // Method to find all chat rooms associated with the given member email.
    @Override
    public List<ChatRoom> findChatRoomByMember(String email) {
        return chatRoomRepository.findChatRoomByMember(email);
    }

    // Method to find a chat room by given product id and buyer id.
    @Override
    public ChatRoom findChatRoomByBuyer(Long productId, Long buyerId) {
        return chatRoomRepository.findByProductIdAndBuyerId(productId, buyerId)
                .orElseGet(()-> new ChatRoom());
    }

    // Method to save a chat into the database.
    @Override
    @Transactional
    public Long saveChat(ChatDto chatDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatDto.getRoomId()).get();
        Chat chat = chatDto.toEntity(chatRoom);
        chatRepository.save(chat);
        System.out.println("이거 보자 " + chatDto.toString());
        System.out.println("이거 보자 " + chatRoom.getId());
        System.out.println("이거 보자 " + chat.getId());
        return chat.getId();
    }

    // Method to find all chat rooms by given product id.
    @Override
    public List<ChatRoom> findByProductId(Long productId){
        return chatRoomRepository.findByProductId(productId);
    }

    // Method to find all chats in a chat room by given room id.
    @Override
    public List<Chat> findChatList(Long roomId){
        return chatRepository.findByChatRoomId(roomId);
    }

    // Method to find a chat room by room name, or create one if it doesn't exist.
    @Override
    @Transactional
    public ChatRoom findChatRoomByName(Member member, String roomName, Long productId) {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findByName(roomName);
        Product product = productRepository.findById(productId).get();
        if(!findChatRoom.isPresent()){
            ChatRoom newChatRoom = ChatRoom.builder()
                    .roomName(roomName)
                    .buyer(member)
                    .product(product).build();
            chatRoomRepository.save(newChatRoom);
            return newChatRoom;
        }
        return findChatRoom.get();
    }

}

