package gachon.mudang.chat.domain;

import lombok.Builder;
import lombok.Getter;
import gachon.mudang.member.domain.Member;
import gachon.mudang.product.domain.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_room")
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Maps to the 'name' column in the 'chat_room' table.
    @Column(name = "name")
    private String name;

    // Establishes a many-to-one relationship between ChatRoom and Product.
    // FetchType.LAZY means that the fetching is lazy-loaded
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Establishes a many-to-one relationship between ChatRoom and Member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    // Establishes a one-to-many relationship from ChatRoom to Chat, with ChatRoom being the owning side of the relationship.
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();

    // The default constructor, generating a unique name for each chat room instance using UUID.
    public ChatRoom() {
        this.name = UUID.randomUUID().toString();
    }

    // The builder pattern is applied to this class, making it easier to create ChatRoom objects in case of many parameters.
    @Builder
    public ChatRoom(Long roomId, String roomName, Product product, Member buyer) {
        this.id = roomId;
        this.name = roomName;
        this.product = product;
        this.buyer = buyer;
    }

    // A business logic method that gets the last chat in the chat room.
    public Chat getLastChat(){
        return getChats().get(getChats().size()-1);
    }
}

