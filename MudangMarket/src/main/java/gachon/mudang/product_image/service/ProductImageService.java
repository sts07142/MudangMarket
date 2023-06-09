package gachon.mudang.product_image.service;

import gachon.mudang.product.domain.Product;
import gachon.mudang.product_image.domain.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {

    /**
     * Save a product image.
     */
    ProductImage save(ProductImage productImage);

    /**
     * Convert a multipart file to a product image associated with a specific product.
     */
    ProductImage convert(MultipartFile multipartFile, Product product) throws IOException;

    /**
     * Find a product image by its URL.
     */
    ProductImage findByUrl(String url);
}
