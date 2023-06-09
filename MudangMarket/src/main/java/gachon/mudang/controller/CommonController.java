package gachon.mudang.controller;

import lombok.RequiredArgsConstructor;
import gachon.mudang.member.dto.MemberJoinRequest;
import gachon.mudang.member.dto.MemberLoginRequest;
import gachon.mudang.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A controller that handles HTTP requests accessible to all users
 * Start screen
 * Login screen
 * Member registration screen
 * Error screen
 * */
@Controller
@RequiredArgsConstructor
public class CommonController {
    private final MemberService memberService;

    // Maps "/" GET requests to this method
    @GetMapping("/")
    public String startPage(){
        // Returns the name of the view to be rendered
        return "start";
    }

    // Maps "/error" GET requests to this method
    @GetMapping("/error")
    public String errorPage(){
        // Returns the name of the error view to be rendered
        return "error";
    }

    // Maps "/join" GET requests to this method
    @GetMapping("/join")
    public String joinPage(Model model){
        // Adds a new MemberJoinRequest to the model
        model.addAttribute("form", new MemberJoinRequest());
        // Returns the name of the member join view to be rendered
        return "members/join";
    }

    // Maps "/join" POST requests to this method
    @PostMapping("/join")
    public String join(@ModelAttribute MemberJoinRequest form){
        // Converts the request form to a Member entity and passes it to the service for processing
        memberService.join(form.toMemberEntity());
        // Redirects the client to the start page
        return "redirect:/";
    }

    // Maps "/login" GET requests to this method
    @GetMapping("/login")
    public String loginPage(Model model){
        // Adds a new MemberLoginRequest to the model
        model.addAttribute("form", new MemberLoginRequest());
        // Returns the name of the login view to be rendered
        return "login";
    }

    // Maps "/logout" GET requests to this method
    @GetMapping("/logout")
    public String logoutPage(){
        // Returns the name of the logout view to be rendered
        return "logout";
    }
}
