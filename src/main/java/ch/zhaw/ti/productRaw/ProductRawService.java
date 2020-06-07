package ch.zhaw.ti.productRaw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductRawService {

    @Autowired
    private ProductRawRepository productRepository;

    public ProductRaw newProductFromUrl(String url){
        ProductRaw product = new ProductRaw();
        product.setUrl(url);

        boolean exists = findByUrl(url).isPresent();

        // Return Entity if Exists
        if(!exists){
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    public Optional<ProductRaw> findByUrl(String url){
        return productRepository.findByUrl(url);
    }

    public List<ProductRaw> findAll(){
        return productRepository.findAll();
    }

    public void save(ProductRaw product){
        productRepository.save(product);
    }
}
