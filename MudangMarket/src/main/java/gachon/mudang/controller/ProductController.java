import gachon.mudang.product.domain.ProductCategory;
import gachon.mudang.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import gachon.mudang.chat.service.ChattingService;
import gachon.mudang.member.domain.Member;
import gachon.mudang.member.service.MemberService;
import gachon.mudang.product.domain.Product;
import gachon.mudang.product.domain.ProductStatus;
import gachon.mudang.product.dto.ProductRegisterRequest;
import gachon.mudang.product.dto.ProductUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final MemberService memberService;
    private final ChattingService chattingService;
    private final List<String> categoryList = getCategoryList(); // getting a list of categories

    // Method to handle GET requests for displaying a list of all products
    @GetMapping("/list")
    public String productsPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Member member = memberService.findMember(userDetails.getUsername());
        model.addAttribute("nickname", member.getNickName()); // Adding nickname to the model
        model.addAttribute("productList", productService.findAllProducts()); // Adding list of all products to the model
        model.addAttribute("interestByMember", member.getProductByInterest()); // Adding products that the member is interested in to the model
        return "products/productList"; // Returning the view to be rendered
    }

    // Method to handle GET requests for navigating to the product registration page
    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("form", new ProductRegisterRequest()); // Adding an empty ProductRegisterRequest to the model
        model.addAttribute("categoryList", categoryList); // Adding the category list to the model
        return "products/register"; // Returning the view to be rendered
    }

    // Method to handle POST requests for registering a product
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.FOUND)
    public String register(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute ProductRegisterRequest form, RedirectAttributes redirectAttributes) throws IOException {
        Long productId = productService.save(form, userDetails.getUsername()); // Saving the product and getting the productId
        redirectAttributes.addAttribute("productId", productId); // Adding productId to the redirectAttributes
        return "redirect:/products/list/{productId}"; // Redirecting to the product detail page
    }

    // Method to handle GET requests for navigating to a product detail page
    @GetMapping("/list/{productId}")
    public String productDetailPage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("productId") Long productId, Model model){
        Member member = memberService.findMember(userDetails.getUsername()); // Fetching the member details
        Product product = productService.findOne(productId); // Fetching the product details
        String roomName = chattingService.findChatRoomByBuyer(productId, member.getId()).getName(); // Fetching the chat room details
        model.addAttribute("memberId", member.getId()); // Adding memberId to the model
        model.addAttribute("changeableStatus", productService.getChangeableProductStatus(product.getStatus())); // Adding changeableStatus to the model
        model.addAttribute("productDetail", product.toProductDetail()); // Adding product details to the model
        model.addAttribute("product", product); // Adding the product to the model
        model.addAttribute("interestList", member.getProductByInterest()); // Adding products that the member is interested in to the model
        model.addAttribute("roomName", roomName); // Adding roomName to the model
        return "products/detail"; // Returning the view to be rendered
    }

    // Method to handle GET requests for displaying a list of products based on the product status and seller's memberId
    @GetMapping("/list/other")
    public String otherProductPage(@Nullable @RequestParam("status") ProductStatus status, @RequestParam("memberId") Long memberId, Model model){
        model.addAttribute("otherProducts", memberService.findOne(memberId).getProductByStatus(status)); // Adding products with given status to the model
        model.addAttribute("memberId", memberId); // Adding memberId to the model
        return "products/other"; // Returning the view to be rendered
    }

    // Method to handle GET requests for updating the product status
    @GetMapping("/update/status")
    public String updateProductStatus(@RequestParam("productId") Long productId, @RequestParam("status") ProductStatus status, Model model){
        productService.updateProductStatus(productId, status); // Updating the product status
        model.addAttribute("state", status.getValue()); // Adding the updated status to the model
        return "products/update-success"; // Returning the view to be rendered
    }

    // Method to handle GET requests for navigating to the product update page
    @GetMapping("/update")
    public String updateProductPage(@RequestParam("productId") Long productId ,Model model){
        Product product = productService.findOne(productId); // Fetching the product details
        model.addAttribute("categoryList", getCategoryList()); // Adding the category list to the model
        model.addAttribute("product", product); // Adding the product to the model
        model.addAttribute("form", ProductUpdateRequest.builder() // Building and adding a ProductUpdateRequest to the model
                .title(product.getTitle())
                .category(product.getCategory())
                .content(product.getContent())
                .price(product.getPrice())
                .build());
        return "products/update"; // Returning the view to be rendered
    }

    // Method to handle POST requests for updating a product
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.FOUND)
    public String updateProduct(@ModelAttribute ProductUpdateRequest form, @RequestParam("productId") Long productId, RedirectAttributes redirectAttributes){
        productService.updateProduct(productId, form.toProductEntity()); // Updating the product
        redirectAttributes.addAttribute("productId", productId); // Adding productId to the redirectAttributes
        return "redirect:/products/list/{productId}"; // Redirecting to the product detail page
    }

    // Method to handle GET requests for navigating to the product deletion confirmation page
    @GetMapping("/delete/check")
    public String deleteProductPage(@RequestParam("productId") Long productId, Model model){
        model.addAttribute("productId", productId); // Adding productId to the model
        return "products/delete"; // Returning the view to be rendered
    }

    // Method to handle GET requests for deleting a product
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("productId") Long productId){
        productService.deleteProduct(productId); // Deleting the product
        return "redirect:/products/list"; // Redirecting to the product list page
    }

    // Method to handle GET requests for displaying a list of chats related to a product
    @GetMapping("/list/{productId}/chat")
    public String productChatListPage(@PathVariable("productId") Long productId, Model model){
        model.addAttribute("productId", productId); // Adding productId to the model
        model.addAttribute("chatList", chattingService.findByProductId(productId)); // Adding list of chats related to the product to the model
        return "products/chat"; // Returning the view to be rendered
    }

    // Private method to fetch a list of product categories
    private List<String> getCategoryList(){
        return Stream.of(ProductCategory.values()) // Creating a stream of ProductCategory values
                .map(m -> m.getValue()) // Mapping each ProductCategory to its value
                .collect(Collectors.toList()); // Collecting the results to a list
    }

}
