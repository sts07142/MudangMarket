package gachon.mudang.chat.repository;

import gachon.mudang.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // Query to find chat rooms where the given email is associated with the buyer or the product seller.
    @Query("select r from ChatRoom r where r.buyer.email= :email or r.product.seller.email= :email")
    List<ChatRoom> findChatRoomByMember(@Param("email") String email);

    // Method to find a chat room based on the product id and buyer id.
    Optional<ChatRoom> findByProductIdAndBuyerId(Long productId, Long buyerId);

    // Method to find a chat room by its name.
    Optional<ChatRoom> findByName(String name);

    // Method to find all chat rooms associated with a given product id.
    List<ChatRoom> findByProductId(Long productId);
}

