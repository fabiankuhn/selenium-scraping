package ch.zhaw.ti.productRaw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRawService {

    @Autowired
    private ProductRawRepository productRepository;

    public void newProductFromUrl(String url){
        ProductRaw product = new ProductRaw();
        product.setUrl(url);
        productRepository.save(product);
    }

    public List<ProductRaw> findAll(){
        return productRepository.findAll();
    }

    public void save(ProductRaw product){
        productRepository.save(product);
    }
}
