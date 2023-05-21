package numble.karrot.product_image.service;

import lombok.RequiredArgsConstructor;
import numble.karrot.image.ImageStorageFolderName;
import numble.karrot.product.domain.Product;
import numble.karrot.product_image.domain.ProductImage;
import numble.karrot.product_image.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService{

    private final ProductImageRepository productImageRepository;

    @Override
    public ProductImage save(ProductImage productImage) {
        productImageRepository.save(productImage);
        return productImage;
    }

    @Override
    public ProductImage convert(MultipartFile multipartFile, Product product) throws IOException{

//        String url = findByUrl.
        return ProductImage.builder()
//                .url(url)
//                // .fileName을 변경 필요
//                .fileName(url)
                .product(product)
                .build();
    }

    @Override
    public ProductImage findByUrl(String url) {
        return productImageRepository.findByUrl(url);
    }
}
