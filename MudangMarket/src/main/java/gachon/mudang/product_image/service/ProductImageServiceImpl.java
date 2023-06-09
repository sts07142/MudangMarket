package gachon.mudang.product_image.service;

import lombok.RequiredArgsConstructor;
import gachon.mudang.product.domain.Product;
import gachon.mudang.product_image.domain.ProductImage;
import gachon.mudang.product_image.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Override
    public ProductImage save(ProductImage productImage) {
        // Save the product image using the product image repository
        productImageRepository.save(productImage);
        return productImage;
    }

    @Override
    public ProductImage convert(MultipartFile multipartFile, Product product) throws IOException {
        // Convert the multipart file to a product image associated with the given product
        // In this example, a fixed URL is used as a placeholder for the product image URL
        return ProductImage.builder()
                .url("https://i.ibb.co/7r2cJ4W/Kakao-Talk-20230525-224725973.png")
                .product(product)
                .build();
    }

    @Override
    public ProductImage findByUrl(String url) {
        // Find a product image by its URL using the product image repository
        return productImageRepository.findByUrl(url);
    }
}
