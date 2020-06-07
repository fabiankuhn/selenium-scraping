package ch.zhaw.ti.scrape;

import ch.zhaw.ti.productRaw.ProductRaw;
import ch.zhaw.ti.productRaw.ProductRawService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductScraper {

    @Autowired
    private ProductRawService productService;

    @Autowired
    private WebDriver firefoxWebDriver;

    /**
     * Scrape product-details
     */
    public void scrape(){

        // js Executor
        JavascriptExecutor js = ((JavascriptExecutor) firefoxWebDriver);

        // Get Products with URL
        List<ProductRaw> productList = productService.findAll();

        // Loop Products
        for(ProductRaw product: productList){

            // FIXME Was scraped with wrong price
            // try{
            //     String price = product.getPrice()
            //             .replace(".–", "")
            //             .replaceAll("[^\\d.]","");
            //     if(Double.parseDouble(price) < 6000 && Double.parseDouble(price) > 0){
            //         continue;
            //     }
            // }catch (Exception ignored){ }

            // Open Product by URL
            firefoxWebDriver.get(product.getUrl());

            // Show all information
            try{
                List<WebElement> buttons = firefoxWebDriver.findElements(By.xpath("//button[text()='Mehr anzeigen']"));
                buttons.forEach(b -> {
                    js.executeScript("arguments[0].click();", b);
                });
            }catch(Exception e){
                System.err.println(e.toString());
            }

            // Preis
            try{
                product.setPrice(firefoxWebDriver.findElement(By.className("Z18w")).getText().split("statt")[0]);
            } catch(Exception e){
                System.err.println(e.toString());
            }

            // Resolution
            List<WebElement> resolutionList = firefoxWebDriver.findElements(By.xpath("//td[text()='Bildauflösung']/following-sibling::td[1]/div"));
            for(WebElement element: resolutionList){

                if(element.getText().contains(" x ")){
                    product.setDisplayResolution(element.getText());
                } else {
                    product.setDisplayResolutionType(element.getText());
                }
            }

            // Amount USB
            try{
                List<WebElement> usbList = firefoxWebDriver.findElements(By.xpath("//td[text()[contains(.,'USB')]]/following-sibling::td[1]/div"));
                int usbSum = usbList.stream()
                        .map(e -> Integer.parseInt(e.getText().replace("x", "").replace(" ", "")))
                        .reduce(0, Integer::sum);
                product.setAmountUSB(String.valueOf(usbSum));
            } catch(Exception ignored){ }



            // Set other Attributes
            product.setManufacturer(getSibling("Hersteller"));
            product.setProductType(getSibling("Produkttyp"));
            product.setOperationSystem(getSibling("Betriebssystem"));
            product.setOperationSystemWindows(getSibling("Windows Version"));
            product.setOperationSystemMac(getSibling("Mac OS X Version"));
            product.setDriveType(getSibling("Datenträgertyp"));
            product.setDriveCapacity(getSibling("Speicherkapazität"));
            product.setDriveCapacitySSD(getSibling("Kapazität SSD"));
            product.setProcessorFamily(getSibling("Prozessor-Familie"));
            product.setProcessorType(getSibling("Prozessortyp"));
            product.setProcessorClockFrequency(getSibling("Prozessor Taktfrequenz"));
            product.setProcessorClockFrequencyMax(getSibling("Max. Turbo-Taktfrequenz"));
            product.setProcessorCores(getSibling("Anzahl Prozessorkerne"));
            product.setProcessorThreads(getSibling("Anzahl Threads"));
            product.setRamAmount(getSibling("Totaler Arbeitsspeicher"));
            product.setRamOnBoard(getSibling("Onboard Arbeitsspeicher"));
            product.setRamMax(getSibling("Max. unterstützter Arbeitsspeicher"));
            product.setRamFrequency(getSibling("Speichertaktfrequenz"));
            product.setGraphicsPerformance(getSibling("Grafikleistung"));
            product.setGraphicCard(getSibling("Grafikkarten Modell"));
            product.setGraphicCardFamily(getSibling("Grafikkartenfamilie"));
            product.setSystemArchitecture(getSibling("Betriebssystem Architektur"));
            product.setDeviceHeight(getSibling("Höhe"));
            product.setDeviceLength(getSibling("Länge"));
            product.setDeviceWidth(getSibling("Breite"));
            product.setDeviceWeight(getSibling("Gewicht"));
            product.setDeviceMaterial(getSibling("Material"));
            product.setBatteryCells(getSibling("Anzahl Zellen"));
            product.setBatteryType(getSibling("Akku-Typ"));
            product.setBatteryCapacity(getSibling("Akkuleistung"));
            product.setBatteryMaxRunTime(getSibling("Max. Laufzeit"));
            product.setBatteryPowerSupply(getSibling("Netzteilstärke"));
            product.setDisplayScale(getSibling("Bildseitenverhältnis"));
            product.setDisplayTechnology(getSibling("Bildschirmtechnologie"));
            product.setWLanController(getSibling("WLAN Kontroller"));
            product.setWLanType(getSibling("WLAN Standard"));
            product.setBluetoothVersion(getSibling("Bluetooth Version"));
            product.setEthernetPortSpeed(getSibling("Max. Port Geschwindigkeit"));
            product.setAmountHDMI(getSibling("HDMI Type A"));
            product.setAmountJackInput(getSibling("Klinke 3.5mm"));
            product.setAmountDocking(getSibling("Docking Anschluss"));
            product.setCameraExists(getSibling("Frontkamera"));
            product.setCameraResolution(getSibling("Frontkamera Auflösung"));
            product.setCameraVideoResolution(getSibling("Frontkamera Videoauflösung"));
            product.setSecurity(getSibling("Sicherheit"));
            product.setOpticalDrive(getSibling("Optisches Laufwerktyp"));
            product.setSalesRanking(getSibling("Verkaufsrang in Produkttyp Notebook"));


            // Update Product
            productService.save(product);
        }

    }


    /**
     * Get sibling text of table
     */
    private String getSibling(String text) {

        try{
            return getTextNode(firefoxWebDriver.findElement(By.xpath("//td[text()='" + text + "']/following-sibling::td[1]/div")));
        } catch(Exception ignored){
            return null;
        }
    }

    /**
     * Remove Text of child nodes
     */
    public static String getTextNode(WebElement e) {
        String text = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {

            // Exclude links
            if(!child.getTagName().equals("a")){
                text = text.replaceFirst(child.getText(), "").trim();
            }
        }
        return text;
    }
}
