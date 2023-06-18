package gachon.mudang.product.service;

import gachon.mudang.product.domain.Product;
import gachon.mudang.product.domain.ProductStatus;
import gachon.mudang.product.dto.ProductRegisterRequest;
import gachon.mudang.product_image.domain.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Product Service Interface
 */
public interface ProductService {
    Long save(ProductRegisterRequest request, String email);
    Product findOne(Long id);
    List<Product> findAllProducts();
    void updateThumbnail(List<ProductImage> images, Long productId);
    void deleteProduct(Long productId);
    void updateProduct(Long id, Product product);
    void updateProductStatus(Long id, ProductStatus status);
    List<ProductStatus> getChangeableProductStatus(ProductStatus status);
    List<ProductImage> getProductImage(List<MultipartFile> productImages, Product product);
}
