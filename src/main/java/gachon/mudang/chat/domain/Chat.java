package gachon.mudang.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "chat")
@Getter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This establishes a many-to-one relationship between the Chat entity and the ChatRoom entity.
    // FetchType.LAZY means that the default fetching strategy is lazy loading.
    // CascadeType.PERSIST means that related entities should also get persisted in the database whenever this entity is persisted.
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    // This maps an enum to a database column.
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ChatType type;

    // Represents the 'profile' column in the database.
    @Column(name = "profile")
    private String profile;

    // Represents the 'nickname' column in the database.
    @Column(name = "nickname")
    private String nickname;

    // Represents the 'content' column in the database.
    @Column(name = "content")
    private String content;

    // This annotation triggers the builder pattern for this class,
    // which makes object creation easier in situations with many parameters.
    @Builder
    public Chat(ChatType type, String profile, String nickname, String content) {
        this.type = type;
        this.profile = profile;
        this.nickname = nickname;
        this.content = content;
    }

    // This is a convenience method for setting the bi-directional relationship between Chat and ChatRoom.
    public void addChat(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
    }
}

