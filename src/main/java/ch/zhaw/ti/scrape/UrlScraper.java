package ch.zhaw.ti.scrape;

import ch.zhaw.ti.productRaw.ProductRawService;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlScraper {

    @Autowired
    private ProductRawService productService;

    @Autowired
    private WebDriver firefoxWebDriver;

    /**
     * Scrape URL of product category.
     */
    public void scrape() throws InterruptedException {

        String productId = "zelt-1462";
        int footerHeight = 100;
        double pageScroll = 0.5;
        String overviewUrl = "https://www.galaxus.ch/de/s3/producttype/" + productId + "?take=" + 100;

        // Open with Selenium
        firefoxWebDriver.get(overviewUrl);

        // Javascript executor
        JavascriptExecutor js = ((JavascriptExecutor) firefoxWebDriver);

        // Prepare Loop
        Long windowHeight = (Long) js.executeScript("return window.innerHeight");
        double windowHeightSum = windowHeight;
        List<WebElement> webElements;
        boolean bottomReached;

        // Loop through visible Products
        while(true){
            // Scroll to bottom in steps
            Thread.sleep(1000);

            // Find WebElements
            webElements = firefoxWebDriver.findElements(By.xpath("//a[contains(@href, '/de/s3/product/') and not(*) and position() >= 0]"));

            // Scroll window height
            js.executeScript("window.scrollTo(0, "+ windowHeightSum +")");

            // Increase WindowHeightSum
            windowHeightSum += (windowHeight * pageScroll);

            for(WebElement webElement: webElements){
                try{
                    // Add to Database
                    productService.newProductFromUrl(webElement.getAttribute("href"));
                } catch(StaleElementReferenceException ignored){ }
            }

            // End scroll if bottom is reached
            bottomReached = bottomReached(js, footerHeight);
            if(bottomReached){

                // Click button "show more"
                firefoxWebDriver.findElement(By.xpath("//button[text()='Mehr anzeigen']")).click();
                Thread.sleep(30000);

                // All items found
                if(bottomReached(js, footerHeight)){
                    break;
                }
            }
        }
    }

    /**
     * Check if bottom of page is reached
     */
    private boolean bottomReached (JavascriptExecutor js, int footerHeight) {
        return (boolean) js.executeScript("return "
                + "(window.innerHeight + window.scrollY) >= (document.body.offsetHeight - "
                + footerHeight + ")");
    }
}
