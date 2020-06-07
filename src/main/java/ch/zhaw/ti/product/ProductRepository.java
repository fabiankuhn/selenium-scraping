package ch.zhaw.ti.product;

import ch.zhaw.ti.productRaw.ProductRaw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
