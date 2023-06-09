package gachon.mudang.controller;

import gachon.mudang.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.chat.service.ChattingService;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.dto.MemberUpdateRequest;
import gachon.mudang.member.service.MemberService;
import gachon.mudang.product.domain.ProductStatus;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("my-page")
public class MyPageController {
    private final MemberService memberService;
    private final ProductServiceImpl productService;
    private final ChattingService chattingService;

    // Method to handle GET requests to "/my-page"
    @GetMapping()
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        // Add authenticated user's details to the model
        model.addAttribute("member", memberService.findMember(userDetails.getUsername()));
        // Returns the name of the view to be rendered
        return "my-page/main";
    }

    // Method to handle GET requests to "/my-page/profile"
    @GetMapping("/profile")
    public String profileUpdatePage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        // Fetch the member's details
        Member member = memberService.findMember(userDetails.getUsername());
        // Add the member and a new MemberUpdateRequest form to the model
        model.addAttribute("member", member);
        model.addAttribute("form", MemberUpdateRequest.builder().nickName(member.getNickName()).build());
        // Returns the name of the view to be rendered
        return "my-page/profile";
    }

    // Method to handle POST requests to "/my-page/profile/{memberId}"
    @PostMapping("/profile/{memberId}")
    @ResponseStatus(HttpStatus.FOUND)
    public String profileUpdate(
            @PathVariable(name = "memberId") Long memberId,
            @ModelAttribute MemberUpdateRequest form) throws IOException {
        // Update member's profile
        memberService.update(memberId, form);
        // Redirects the client to the "/my-page"
        return "redirect:/my-page";
    }

    // Method to handle GET requests to "/my-page/product"
    @GetMapping("/product")
    public String productDetailsPage(@AuthenticationPrincipal UserDetails userDetails, @Nullable @RequestParam("status") ProductStatus status, Model model){
        // Fetch the member's details
        Member member = memberService.findMember(userDetails.getUsername());
        // Add the member's products, interests, and changeable status to the model
        model.addAttribute("products", member.getProductByStatus(status));
        model.addAttribute("interestByMember", member.getProductByInterest());
        model.addAttribute("changeableStatus", productService.getChangeableProductStatus(status));
        // Returns the name of the view to be rendered
        return "my-page/product";
    }

    // Method to handle GET requests to "/my-page/interest"
    @GetMapping("/interest")
    public String interestDetailsPageByStatus(@AuthenticationPrincipal UserDetails userDetails, @Nullable @RequestParam("status") ProductStatus status, Model model){
        // Fetch the member's details
        Member member = memberService.findMember(userDetails.getUsername());
        // Add the member's interested products to the model
        model.addAttribute("interestProducts", member.getInterestStatus(status));
        // Returns the name of the view to be rendered
        return "my-page/interest";
    }

    // Method to handle GET requests to "/my-page/chat"
    @GetMapping("/chat")
    public String findRoomsByMemberPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        // Fetch the chatrooms associated with the member
        List<ChatRoom> chatList = chattingService.findChatRoomByMember(userDetails.getUsername());

        // Some logging statements
        System.out.println("이거 보자 " + userDetails.getUsername());
        System.out.println("이거 보자 " + chatList.size());
        for (int i = 0; i < chatList.size(); i++) {
            System.out.println("챗리스트 반복문 : " + chatList.get(i).toString());
        }

        // Add the user's email and chatrooms to the model
        model.addAttribute("userEmail", userDetails.getUsername());
        model.addAttribute("chatList", chatList);
        // Returns the name of the view to be rendered
        return "my-page/chat";
    }
}
