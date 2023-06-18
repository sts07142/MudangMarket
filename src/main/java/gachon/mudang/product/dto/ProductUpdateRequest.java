package gachon.mudang.product.dto;

import gachon.mudang.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * This DTO class represents a request to update a product.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateRequest {

    private String title;
    private int price;
    private String content;
    private String category;
    List<MultipartFile> productImages;

    @Builder
    public ProductUpdateRequest(String title, int price, String content, String category, List<MultipartFile> productImages) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
        this.productImages = productImages;
    }

    /**
     * Convert to Product Entity
     * This method converts the ProductUpdateRequest object to a Product entity object.
     * @return Product Entity
     */
    public Product toProductEntity(){
        return Product.builder()
                .title(this.getTitle())
                .price(this.getPrice())
                .content(this.getContent())
                .category(this.getCategory())
                .build();
    }
}
