package gachon.mudang.product.dto;

import gachon.mudang.product.domain.Product;
import lombok.*;

import java.util.List;

/**
 * DTO for Product Details Response
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDetailsResponse {
    private String nickName;
    private String profile;
    private List<String> productImages;
    private String title;
    private int price;
    private String category;
    private String date;
    private String content;
    private int interestCount;
    private List<Product> otherProducts;
    private String status;
}
