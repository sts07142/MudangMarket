package gachon.mudang.controller;

import lombok.RequiredArgsConstructor;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.chat.service.ChattingService;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class ChatRoomController {
    private final ChattingService chattingService;
    private final MemberService memberService;

    // Handler method for GET requests on "/chat/room"
    // UserDetails object is authenticated user, product id and room name are required parameters
    @GetMapping("/room")
    public String enterPage(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam("productId") Long productId,
                            @RequestParam("roomName") String roomName,
                            Model model){
        // Fetch the member from database by username
        Member member = memberService.findMember(userDetails.getUsername());

        // Find a chatroom by name
        ChatRoom room = chattingService.findChatRoomByName(member, roomName, productId);

        // Add attributes to the model that will be used in the view
        model.addAttribute("product", room.getProduct());
        model.addAttribute("chatList", chattingService.findChatList(room.getId()));
        model.addAttribute("roomId", room.getId());
        model.addAttribute("profile", member.getProfile());
        model.addAttribute("nickname", member.getNickName());

        // Return view name
        return "chatting/chat";
    }
}
