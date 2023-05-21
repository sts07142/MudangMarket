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
    private Long roomId;
    private ChatType type;
    private String profile;
    private String nickname;
    private String content;

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
