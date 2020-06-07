package ch.zhaw.ti.product;

import ch.zhaw.ti.productRaw.ProductRaw;
import ch.zhaw.ti.productRaw.ProductRawService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import java.lang.reflect.Method;

@Service
public class ProductConverter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRawService productRawService;

    public void convert(){

        ModelMapper modelMapper = new ModelMapper();

        final String[] convertToInt = {
                "DriveCapacity",
                "DriveCapacitySSD",
                "ProcessorCores",
                "ProcessorThreads",
                "RamAmount",
                "RamOnBoard",
                "RamMax",
                "RamFrequency",
                "BatteryCells",
                "BatteryPowerSupply",
                "AmountUSB",
                "AmountHDMI",
                "AmountJackInput"
        };

        final String[] convertToDouble = {
                "DeviceLength",
                "DeviceWidth",
                "DeviceHeight",
                "ProcessorClockFrequency",
                "ProcessorClockFrequencyMax",
                "BatteryCapacity",
                "BatteryMaxRunTime",
                "BluetoothVersion",
                "CameraResolution"
        };

        // Define Post Converter
        Converter<ProductRaw, Product> converter = context -> {

            // Get Source and Destination
            ProductRaw productRaw = context.getSource();
            Product product = context.getDestination();

            // Price
            try{
                product.setPrice(getDouble(getValue(productRaw.getPrice()).split("statt")[0]));
            } catch(Exception ignored) { }

            // Sales rank
            try{
                product.setSalesRanking(getInt(productRaw.getSalesRanking().split(" von")[0]));
            } catch(Exception ignored){ }

            // Weight
            String weight = productRaw.getDeviceWeight();
            if(weight != null){
                if(weight.contains("kg")){
                    product.setDeviceWeight(getDouble(weight) * 1000);
                } else {
                    product.setDeviceWeight(getDouble(weight));
                }
            }

            // System Architecture
            try{
                product.setSystemArchitecture(getInt(productRaw.getSystemArchitecture().split("-bt")[0]));
            } catch(Exception ignored) { }

            // Display Resolution 920 x 1080 Pixels
            try{
                String[] displayResolution = productRaw.getDisplayResolution().split("x");
                product.setDisplayResolutionHeight(getInt(displayResolution[0]));
                product.setDisplayResolutionWidth(getInt(displayResolution[1]));
            } catch(Exception ignored){ }

            // Docking
            try{
                product.setDockingExists(productRaw.getAmountDocking().equals("Ja"));
            }catch(NullPointerException ignored){ }

            // Camera
            try{
                product.setCameraExists(productRaw.getCameraExists().equals("Ja"));
            }catch(NullPointerException ignored){ }

            // Camera Resolution
            try{
                String[] cameraResolution = productRaw.getCameraVideoResolution().split("x");
                product.setCameraVideoResolutionHeight(getInt(cameraResolution[0]));
                product.setCameraVideoResolutionWidth(getInt(cameraResolution[1]));
            } catch(Exception ignored){ }

            // Display Scale
            try{
                String[] scale = productRaw.getDisplayScale().split(":");
                product.setDisplayScale(getDouble(scale[0])/getDouble(scale[1]));
            } catch(Exception ignored){ }

            // Ethernet port Speed
            try{
                product.setEthernetPortSpeed(getInt(productRaw.getEthernetPortSpeed().split(" ")[0]));
            } catch (NullPointerException ignored){ }

            /*
             * Integer Conversions
             */
            for(String methodString: convertToInt){
                try {
                    // Get String
                    Method getNameMethod = productRaw.getClass().getMethod("get" + methodString);
                    String value = (String) getNameMethod.invoke(productRaw); // explicit cast

                    // Save Integer
                    Method setNameMethod = product.getClass().getMethod("set" + methodString, Integer.class);
                    setNameMethod.invoke(product, getInt(value)); // pass arg

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }

            /*
             * Double Conversions
             */
            for(String methodString: convertToDouble){
                try {
                    // Get String
                    Method getNameMethod = productRaw.getClass().getMethod("get" + methodString);
                    String value = (String) getNameMethod.invoke(productRaw); // explicit cast

                    // Save Double
                    Method setNameMethod = product.getClass().getMethod("set" + methodString, Double.class);
                    setNameMethod.invoke(product, getDouble(value)); // pass arg

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }

            return product;
        };

        // Set up TypeMap with standard Conversion
        TypeMap<ProductRaw, Product> typeMap = modelMapper.createTypeMap(ProductRaw.class, Product.class);

        // Skip properties
        PropertyMap<ProductRaw, Product> skipMap = new PropertyMap<>(){
            @Override
            protected void configure() {
                // From custom conversion
                skip(destination.getPrice());
                skip(destination.getSalesRanking());
                skip(destination.getDeviceWeight());
                skip(destination.getSystemArchitecture());
                skip(destination.getCameraExists());
                skip(destination.getDisplayScale());
                skip(destination.getEthernetPortSpeed());

                // From Loops
                skip(destination.getDriveCapacity());
                skip(destination.getDriveCapacitySSD());
                skip(destination.getProcessorCores());
                skip(destination.getProcessorThreads());
                skip(destination.getRamAmount());
                skip(destination.getRamOnBoard());
                skip(destination.getRamMax());
                skip(destination.getRamFrequency());
                skip(destination.getBatteryCells());
                skip(destination.getBatteryPowerSupply());
                skip(destination.getAmountUSB());
                skip(destination.getAmountHDMI());
                skip(destination.getAmountJackInput());
                skip(destination.getDeviceLength());
                skip(destination.getDeviceWidth());
                skip(destination.getDeviceHeight());
                skip(destination.getProcessorClockFrequency());
                skip(destination.getProcessorClockFrequencyMax());
                skip(destination.getBatteryCapacity());
                skip(destination.getBatteryMaxRunTime());
                skip(destination.getBluetoothVersion());
                skip(destination.getCameraResolution());
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

    private Integer getInt(String rawString){
        if(rawString != null){
            return Integer.parseInt(getValue(rawString));
        }
        return null;
    }

    private Double getDouble(String rawString){
        if(rawString != null){
            return Double.parseDouble(getValue(rawString));
        }
        return null;
    }

    private String getValue(String rawString) {
        return rawString
                .replace(".–", "")
                .replaceAll("[^\\d.]","");
    }
}
