package org.example;

import java.util.Map;

public class MachineController {

    public Map<Long, Product> getListProducAndStock(){
        ProductService productService = new ProductService();
        return productService.getAllProduct();
    }

    public Product selectProduct(Long id){
        ProductService productService = new ProductService();
        return productService.getProductById(id);
    }
}
