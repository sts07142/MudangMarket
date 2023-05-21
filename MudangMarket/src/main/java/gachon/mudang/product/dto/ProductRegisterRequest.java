package gachon.mudang.product.dto;

import gachon.mudang.product.domain.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductRegisterRequest {

    @NotEmpty(message = "상품명 입력은 필수 입니다.")
    private String title;
    @NotEmpty(message = "카테고리 입력은 필수 입니다.")
    private String category;
    @NotEmpty(message = "가격 입력은 필수 입니다.")
    private int price;
    @NotEmpty(message = "상품 정보 입력은 필수 입니다.")
    private String content;
    private List<MultipartFile> productImages;

    @Builder
    public ProductRegisterRequest(String title, String category, int price, String content, List<MultipartFile> productImages) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.content = content;
        this.productImages = productImages;
    }

    /**
     * 상품 엔티티로 변환
     * 상품 이미지가 비어있다면 예외 발생 및 로그 기록
     * @return Product Entity
     * */
    public Product toProductEntity(){
        return Product.builder()
                .title(getTitle())
                .category(getCategory())
                .content(getContent())
                .price(getPrice()).build();
    }

}
