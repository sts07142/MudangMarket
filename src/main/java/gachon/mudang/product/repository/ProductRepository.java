package gachon.mudang.product.repository;

import gachon.mudang.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Product Repository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
