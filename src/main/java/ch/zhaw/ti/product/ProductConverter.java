package ch.zhaw.ti.product;

import ch.zhaw.ti.productRaw.ProductRaw;
import ch.zhaw.ti.productRaw.ProductRawService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRawService productRawService;

    public void convert(){

        ModelMapper modelMapper = new ModelMapper();


        // Define Post Converter
        Converter<ProductRaw, Product> converter = context -> {

            // Get Source and Destination
            ProductRaw productRaw = context.getSource();
            Product product = context.getDestination();

            // Price conversion
            String preisRaw = productRaw.getPreis();
            if(preisRaw != null){
                preisRaw = preisRaw.replace(".–", "");
                preisRaw = preisRaw.split("statt")[0];
                product.setPreis(Double.parseDouble(preisRaw));
            }

            // Gewicht conversion
            String gewichtRaw = productRaw.getGewicht();
            if(gewichtRaw != null){
                if(gewichtRaw.contains("kg")){
                    product.setGewicht(getDouble(gewichtRaw) * 1000);
                } else {
                    product.setGewicht(getDouble(gewichtRaw));
                }
            }

            // Länge conversion
            String laenge = productRaw.getLaenge();
            if(laenge != null){
                product.setLaenge(getDouble(laenge));
            }

            // Breite conversion
            String breite = productRaw.getBreite();
            if(breite != null){
                product.setBreite(getDouble(breite));
            }


            // Hohehe conversion
            String hoehe = productRaw.getHoehe();
            if(hoehe != null){
                product.setHoehe(getDouble(hoehe));
            }

            // Stehhöhe conversion
            String stehHoehe = productRaw.getStehHoehe();
            if(stehHoehe != null){

                product.setStehHoehe(getInt(stehHoehe));
            }

            // Wassersäule conversion
            String wasserSaeule = productRaw.getWasserSaeule();
            if(wasserSaeule != null){
                product.setWasserSaeule(getDouble(wasserSaeule));
            }

            // Anzahlräume conversion
            String anzahlRaeume = productRaw.getAnzahlRaeume();
            if(anzahlRaeume != null){
                product.setAnzahlRaeume(Integer.parseInt(anzahlRaeume));
            }

            // Maxanzahl Personen conversion
            String maxAnzahlPersonen = productRaw.getMaxAnzahlPersonen();
            if(maxAnzahlPersonen != null){
                product.setMaxAnzahlPersonen(getInt(maxAnzahlPersonen));
            }

            // Verkaufsrang conversion
            String verkaufsRang = productRaw.getVerkaufsrang();
            if(verkaufsRang != null){
                product.setVerkaufsrang(Integer.parseInt(verkaufsRang.split(" von")[0]));
            }

            // Packmass conversion
            String packMass = productRaw.getPackmass();
            if(packMass != null){
                String[] packMassSplit = packMass.split(" x ");
                product.setPackmassBreite(getDouble(packMassSplit[0]));
                product.setPackmassLaenge(getDouble(packMassSplit[1]));
            }

            // Zeltabmessung conversion
            String zeltAbmessung = productRaw.getZeltAbmessung();
            if(zeltAbmessung != null){
                String[] zeltAbmessungSplit = zeltAbmessung.split(" x ");

                // Breite
                if(zeltAbmessungSplit[0].contains("+")){
                    String[] breiteSplit = zeltAbmessungSplit[0].split("\\+");
                    product.setZeltAbmessungBreite(getInt(breiteSplit[0]) + getInt(breiteSplit[1]));
                }else {
                    product.setZeltAbmessungBreite(getInt(zeltAbmessungSplit[0]));
                }

                // Länge
                if(zeltAbmessungSplit[1].contains("/")){
                    product.setZeltAbmessungLaenge(getInt(zeltAbmessungSplit[1].split("/")[0]));
                } else {
                    product.setZeltAbmessungLaenge(getInt(zeltAbmessungSplit[1]));
                }

            }

            return product;
        };

        // Set up TypeMap with standard Conversion
        TypeMap<ProductRaw, Product> typeMap = modelMapper.createTypeMap(ProductRaw.class, Product.class);

        // Skip properties
        PropertyMap<ProductRaw, Product> skipMap = new PropertyMap<ProductRaw, Product>(){
            @Override
            protected void configure() {
                skip(destination.getPreis());
                skip(destination.getGewicht());
                skip(destination.getLaenge());
                skip(destination.getBreite());
                skip(destination.getHoehe());
                skip(destination.getStehHoehe());
                skip(destination.getWasserSaeule());
                skip(destination.getAnzahlRaeume());
                skip(destination.getMaxAnzahlPersonen());
                skip(destination.getVerkaufsrang());
            }
        };

        // Add skipMap
        typeMap.addMappings(skipMap);

        // Add Converter
        typeMap.setPreConverter(converter);

        // Convert
        productRawService.findAll()
                .stream()
                .map(typeMap::map)
                .forEach(productRepository::save);

    }


    private int getInt(String rawString){
        return Integer.parseInt(getValue(rawString));
    }

    private double getDouble(String rawString){
        return Double.parseDouble(getValue(rawString));
    }

    private String getValue(String rawString) {
        return rawString
                .replace(" ", "")
                .replace("cm", "")
                .replace("kg", "")
                .replace("g", "")
                .replace("mm", "")
                .replace("Pers.", "");
    }
}


