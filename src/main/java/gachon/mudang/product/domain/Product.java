package gachon.mudang.product.domain;

import gachon.mudang.TimeService;
import gachon.mudang.chat.domain.ChatRoom;
import gachon.mudang.interest.domain.Interest;
import gachon.mudang.member.domain.Member;
import gachon.mudang.product_image.domain.ProductImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import gachon.mudang.product.dto.ProductDetailsResponse;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "place")
    private String place;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "interest_count")
    private int interestCount;

    @Column(name = "chatting_count")
    private int chattingCount;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member seller;

    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<Interest> interests = new ArrayList<>();

    // Constructor for creating a Product with basic information
    @Builder
    public Product(String title, String category, int price, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.price = price;
        this.status = ProductStatus.TRADE;
        this.interestCount = 0;
        this.chattingCount = 0;
        this.place = "서울";
        this.date = TimeService.getPresentTime();
    }

    // Convenience method for establishing the association between a product and a seller
    public void addProduct(Member seller){
        this.seller = seller;
        seller.getProducts().add(this);
    }

    /* Business Logic */

    // Set the status of the product
    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    // Set the thumbnail of the product and update the associated product images
    public void setThumbnail(List<ProductImage> productImages) {
        this.thumbnail = productImages.get(0).getUrl();
        this.productImages = productImages;
    }

    // Update the product information
    public Product update(Product product){
        title = product.getTitle();
        price = product.getPrice();
        content = product.getContent();
        category = product.getCategory();
        return this;
    }

    // Increase the interest count of the product
    public void addInterestCount(){
        this.interestCount++;
    }

    // Decrease the interest count of the product
    public void reduceInterestCount(){
        this.interestCount--;
    }

    // Increase the chatting count of the product
    public void addChattingCount(){
        this.chattingCount++;
    }

    // Decrease the chatting count of the product
    public void reduceChattingCount(){
        this.chattingCount--;
    }

    /* Query Logic */

    // Get the URLs of product images associated with the product
    public List<String> getProductImageUrl(){
        return productImages.stream().map(i -> i.getUrl()).collect(Collectors.toList());
    }

    // Convert the product details to a response DTO for API output
    public ProductDetailsResponse toProductDetail(){
        return ProductDetailsResponse.builder()
                .productImages(getProductImageUrl())
                .profile(seller.getProfile())
                .otherProducts(seller.getProducts())
                .category(category)
                .content(content)
                .date(TimeService.replaceProductDate(date.toLocalDateTime()))
                .nickName(seller.getNickName())
                .price(price)
                .interestCount(interestCount)
                .title(title)
                .status(status.getValue()).build();
    }

}
