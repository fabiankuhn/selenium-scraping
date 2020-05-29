package ch.zhaw.ti.scrape;

import ch.zhaw.ti.productRaw.ProductRaw;
import ch.zhaw.ti.productRaw.ProductRawService;
import org.openqa.selenium.By;
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

        // Get Products with URL
        List<ProductRaw> productList = productService.findAll();

        // Loop Products
        for(ProductRaw product: productList){

            // Open Product by URL
            firefoxWebDriver.get(product.getUrl());

            // Show all information
            try{
                firefoxWebDriver.findElements(By.xpath("//button[text()='Mehr anzeigen']")).forEach(WebElement::click);
            }catch(Exception e){
                System.err.println(e.toString());
            }

            // Get Product Information
            product.setHersteller(getSibling("Hersteller"));
            product.setVerlag(getSibling("Verlag"));
            product.setProductTyp(getSibling("Produkttyp"));
            product.setVerkaufsrang(getSibling("Verkaufsrang in Produkttyp Zelt"));
            product.setFarbgruppe(getSibling("Farbgruppe"));
            product.setGenaueFarbbezeichnung(getSibling("Genaue Farbbezeichnung"));
            product.setMaterialUeberzelt(getSibling("Material Überzelt"));
            product.setMaterialInnenzelt(getSibling("Material Innenzelt"));
            product.setMaterialBoden(getSibling("Material Boden"));
            product.setMaterialGestaenge(getSibling("Material Gestänge"));
            product.setZeltTyp(getSibling("Zelttyp"));
            product.setZeltAbmessung(getSibling("Zelt Abmessung"));
            product.setPackmass(getSibling("Packmass"));
            product.setMaxAnzahlPersonen(getSibling("Max. Anzahl Personen"));
            product.setAnzahlRaeume(getSibling("Anzahl Räume"));
            product.setGewicht(getSibling("Gewicht"));
            product.setLaenge(getSibling("Länge"));
            product.setBreite(getSibling("Breite"));
            product.setHoehe(getSibling("Höhe"));
            product.setStehHoehe(getSibling("Stehhöhe"));
            product.setWasserSaeule(getSibling("Wassersäule"));

            // Preis
            try{
                product.setPreis(firefoxWebDriver.findElement(By.className("ZZf9")).getText());
            } catch(Exception e){
                System.err.println(e.toString());
            }

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
