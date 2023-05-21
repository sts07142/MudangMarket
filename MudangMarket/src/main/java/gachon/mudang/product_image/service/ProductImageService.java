package gachon.mudang.product_image.service;

import gachon.mudang.product.domain.Product;
import gachon.mudang.product_image.domain.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {
    ProductImage save(ProductImage productImage);
    ProductImage convert(MultipartFile multipartFile, Product product) throws IOException;

    ProductImage findByUrl(String url);
}
