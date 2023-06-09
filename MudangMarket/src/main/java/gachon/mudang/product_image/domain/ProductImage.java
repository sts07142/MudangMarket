package gachon.mudang.product_image.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import gachon.mudang.product.domain.Product;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
@Getter
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "filename")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductImage(String url, String fileName, Product product) {
        this.url = url;
        this.fileName = fileName;
        this.product = product;
        product.getProductImages().add(this);
    }

    /**
     * Convenience method for managing the association with the product.
     * Removes the product image from the product's list of images.
     */
    public void remove() {
        product.getProductImages().remove(this);
    }
}
