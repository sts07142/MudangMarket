package gachon.mudang.chat.dto;

import lombok.*;
import gachon.mudang.chat.domain.Chat;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.chat.domain.ChatType;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatDto {

    // Represents the room id in which the chat message is sent.
    private Long roomId;

    // Represents the type of chat message.
    private ChatType type;

    // Represents the profile of the chat sender.
    private String profile;

    // Represents the nickname of the chat sender.
    private String nickname;

    // Represents the content of the chat message.
    private String content;

    // Method to convert the DTO object to an entity object.
    // This method is typically used when we want to convert incoming DTO into an entity for persistence.
    public Chat toEntity(ChatRoom chatRoom){
        Chat chat = Chat.builder()
                .type(this.type)
                .profile(this.profile)
                .nickname(this.nickname)
                .content(this.content)
                .build();
        chat.addChat(chatRoom);
        return chat;
    }

}

