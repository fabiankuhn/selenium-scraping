package ch.zhaw.ti.scrape;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {

    @Bean
    public WebDriver firefoxWebDriver(){
        // Parameters
        boolean enableProxy = false;
        String proxyUrl = "134.119.207.117:5836"; // http://spys.one/en/https-ssl-proxy/

        // Set GeckoDriver
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");

        // Set up Proxy
        Proxy proxy = new Proxy();
        proxy.setSslProxy(proxyUrl);
        proxy.setHttpProxy(proxyUrl);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("proxy", proxy);
        WebDriver driver;
        //noinspection ConstantConditions
        if(enableProxy){
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            driver = new FirefoxDriver();
        }
        return driver;
    }
}
