package ch.zhaw.ti.productRaw;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRawRepository extends JpaRepository<ProductRaw, Long> {

    Optional<ProductRaw> findByUrl(String url);

}
