https://www.gitmemory.com/issue/modelmapper/modelmapper/464/490810070

// Works with Lambda
// modelMapper.emptyTypeMap(ProductRaw.class, Product.class).setConverter(converter);

// Works with Override Method
// modelMapper.addConverter(converter);




        // Skip Properties
//        typeMap.addMappings(mapper -> mapper.skip(Product::setPreis));
//        typeMap.addMappings(mapper -> mapper.skip(Product::setGewicht));



        // Skip properties
        PropertyMap<ProductRaw, Product> skipMap = new PropertyMap<ProductRaw, Product>(){
            @Override
            protected void configure() {
                skip(destination.getPreis());
                skip(destination.getGewicht());
            }
        };
        



call CSVWRITE ( '/Users/fabiankuhn/Desktop/filename1.txt', 'SELECT * FROM Product' ) 

  
-- script simple columns to '/Users/fabiankuhn/Desktop/FileName.sql' table "Contract";
