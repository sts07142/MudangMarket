package gachon.mudang.product.dto;

import gachon.mudang.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 상품 수정 요청 DTO
 * */
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

    public Product toProductEntity(){
        return Product.builder()
                .title(this.getTitle())
                .price(this.getPrice())
                .content(this.getContent())
                .category(this.getCategory())
                .build();
    }
}
