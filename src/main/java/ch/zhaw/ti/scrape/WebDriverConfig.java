package ch.zhaw.ti.scrape;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@SuppressWarnings("ConstantConditions")
@Configuration
public class WebDriverConfig {

    @Bean
    @Scope("singleton")
    public WebDriver firefoxWebDriver(){
        // Parameters
        boolean enableProxy = false;
        boolean makeHeadless = true;
        String proxyUrl = "134.119.207.114:5836"; // http://spys.one/en/https-ssl-proxy/

        // Set GeckoDriver
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");

        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Make Headless
        firefoxOptions.setHeadless(makeHeadless);

        // Add Proxy
        if(enableProxy){
            Proxy proxy = new Proxy();
            proxy.setSslProxy(proxyUrl);
            proxy.setHttpProxy(proxyUrl);
            firefoxOptions.setCapability("proxy", proxy);
        }

        return new FirefoxDriver(firefoxOptions);
    }
}
