package ch.zhaw.ti.scrape;

import ch.zhaw.ti.productRaw.ProductRaw;
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

        String productId = "notebook-6";
        int footerHeight = 100;
        double pageScroll = 0.4;
        String productPrefix = "s1";
        String overviewUrl = "https://www.galaxus.ch/de/"+ productPrefix + "/producttype/" + productId + "?take=" + 100;

        // Open with Selenium
        firefoxWebDriver.get(overviewUrl);

        // Javascript executor
        JavascriptExecutor js = ((JavascriptExecutor) firefoxWebDriver);

        // Prepare Loop
        Long windowHeight = (Long) js.executeScript("return window.innerHeight");
        double windowHeightSum = windowHeight;
        List<WebElement> webElements;
        boolean bottomReached;
        int counter = 0;

        // Loop through visible Products
        while(true){
            // Scroll to bottom in steps
            Thread.sleep(1000);

            // Find WebElements
            webElements = firefoxWebDriver.findElements(By.xpath("//a[contains(@href, '/de/" + productPrefix + "/product/') and not(*) and position() >= 0]"));

            // Scroll window height
            js.executeScript("window.scrollTo(0, "+ windowHeightSum +")");

            // Increase WindowHeightSum
            windowHeightSum += (windowHeight * pageScroll);

            for(WebElement webElement: webElements){
                try{
                    // Add to Database
                    ProductRaw newEntity = productService.newProductFromUrl(webElement.getAttribute("href"));

                    // Logging
                    if(newEntity != null){
                        System.out.println("Saved Nr " + counter++ + ": " + newEntity.getUrl());
                    }
                } catch(StaleElementReferenceException ignored){ }
            }

            // End scroll if bottom is reached
            bottomReached = bottomReached(js, footerHeight);
            if(bottomReached){

                // Show more elements
                WebElement element = firefoxWebDriver.findElement(By.xpath("//button[text()='Mehr anzeigen']"));
                js.executeScript("arguments[0].click();", element);

                // Wait for Load: ideal Value 15000 2020-06-03
                System.out.println("...Load Products");
                Thread.sleep(20000);

                // All items found
                if(!element.isDisplayed() || !element.isEnabled()){
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
