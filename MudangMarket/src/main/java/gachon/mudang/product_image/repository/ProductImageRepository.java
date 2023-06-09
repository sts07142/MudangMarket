package gachon.mudang.product_image.repository;

import gachon.mudang.product_image.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    /**
     * Find a product image by its URL.
     */
    ProductImage findByUrl(String url);
}
