package gachon.mudang.controller;

import gachon.mudang.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/interests")
public class InterestController {
    private final InterestService interestService;

    // Maps "/interests/save" GET requests to this method
    @GetMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam("productId") Long productId,
                       Model model){
        // Add to the interest list
        interestService.addInterestList(userDetails.getUsername(), productId);
        // Add status message to the model
        model.addAttribute("state", "추가");
        // Returns the name of the view to be rendered
        return "interest-save-and-delete";
    }

    // Maps "/interests/delete" GET requests to this method
    @GetMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails,
                         @RequestParam("productId") Long productId,
                         Model model){
        // Deletes from the interest list
        interestService.deleteInterestByProductList(userDetails.getUsername(), productId);
        // Add status message to the model
        model.addAttribute("state", "삭제");
        // Returns the name of the view to be rendered
        return "interest-save-and-delete";
    }
}
