package gachon.mudang.product_image.repository;

import gachon.mudang.product_image.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByUrl(String url);
}
