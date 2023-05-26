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

    /**
     * 나의 당근 페이지로 이동
     * @param model
     * @return
     */
    @GetMapping()
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("member", memberService.findMember(userDetails.getUsername()));
        return "my-page/main";
    }

    /**
     * 프로필 수정 페이지로 이동
     * @return
     */
    @GetMapping("/profile")
    public String profileUpdatePage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        // 1. 수정할 회원 정보 조회 SELECT
        Member member = memberService.findMember(userDetails.getUsername());
        // 2. View 속성값 등록
        model.addAttribute("member", member);
        model.addAttribute("form", MemberUpdateRequest.builder().nickName(member.getNickName()).build());
        return "my-page/profile";
    }

    @PostMapping("/profile/{memberId}")
    @ResponseStatus(HttpStatus.FOUND)
    public String profileUpdate(
            @PathVariable(name = "memberId") Long memberId,
            @ModelAttribute MemberUpdateRequest form) throws IOException {
        memberService.update(memberId, form);
        return "redirect:/my-page";
    }

    /**
     * 판매내역 페이지로 이동
     * @return
     */
    @GetMapping("/product")
    public String productDetailsPage(@AuthenticationPrincipal UserDetails userDetails, @Nullable @RequestParam("status") ProductStatus status, Model model){
        // 1. 회원 정보 SELECT
        Member member = memberService.findMember(userDetails.getUsername());
        // 2. View 속성값 등록
        model.addAttribute("products", member.getProductByStatus(status));
//        member.getProductByStatus(status)
        model.addAttribute("interestByMember", member.getProductByInterest());
        model.addAttribute("changeableStatus", productService.getChangeableProductStatus(status));
        return "my-page/product";
    }

    @GetMapping("/interest")
    public String interestDetailsPageByStatus(@AuthenticationPrincipal UserDetails userDetails, @Nullable @RequestParam("status") ProductStatus status, Model model){
        // 1. 회원 정보 SELECT
        Member member = memberService.findMember(userDetails.getUsername());
        // 2. View 속성 등록
        model.addAttribute("interestProducts", member.getInterestStatus(status));
        return "my-page/interest";
    }

    /**
     * 채팅 목록 조회 페이지
     * @param userDetails
     * @param model
     * @return
     */
    @GetMapping("/chat")
    public String findRoomsByMemberPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        // 1. 채팅 목록 조회 SELECT
        // List<ChatRoom> chatList = chattingService.findChatRoomByEmail(userDetails.getUsername());
        List<ChatRoom> chatList = chattingService.findChatRoomByMember(userDetails.getUsername());
        
        System.out.println("이거 보자 " + userDetails.getUsername());
        System.out.println("이거 보자 " + chatList.size());
        for (int i = 0; i < chatList.size(); i++) {
            System.out.println("챗리스트 반복문 : " + chatList.get(i).toString());
        }
        // 2. View 속성값 등록
        model.addAttribute("userEmail", userDetails.getUsername());
        model.addAttribute("chatList", chatList);
        return "my-page/chat";
    }
}
