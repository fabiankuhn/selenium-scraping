package ch.zhaw.ti;

import ch.zhaw.ti.product.ProductConverter;
import ch.zhaw.ti.scrape.ProductScraper;
import ch.zhaw.ti.scrape.UrlScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiApplication implements CommandLineRunner {

    @Autowired
    private UrlScraper urlScraper;

    @Autowired
    private ProductScraper productScraper;

    @Autowired
    private ProductConverter productConverter;

    public static void main(String[] args) {
        SpringApplication.run(TiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO: activate for scraping urls
        // urlScraper.scrape();

        // TODO: Activate for product scraping
        // productScraper.scrape();

        // TODO: Activate for product-data conversion
        // productConverter.convert();
    }
}
