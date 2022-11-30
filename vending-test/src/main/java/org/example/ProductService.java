package org.example;

import java.util.HashMap;
import java.util.Map;

public class ProductService {
    public Map<Long, Product> productList = new HashMap<>();

    public void init(){
        productList.put(1L,new Product(1L,"Biskuit",6000.0,10));
        productList.put(2L,new Product(2L,"Chips",8000.0,10));
        productList.put(3L,new Product(3L,"Oreo",10000.0,10));
        productList.put(4L,new Product(4L,"Tango",12000.0,10));
        productList.put(5L,new Product(5L,"Cokelat",15000.0,10));
    }

    public Product getProductById(Long id){
        return productList.get(id);
    }

    public Map<Long, Product> getAllProduct(){
        return productList;
    }

    public void updateProduct(Long id, Product product){
        productList.put(id,product);
    }

    public void storeService(Long id, String productName, Double price, Integer stock) {
        Product product = productList.get(id);
        if (null == product)
            productList.put(id, new Product(id,productName, price, stock));
        else {
            if (null != price && null == stock)
                product = new Product(id,product.getProductName(), price, product.getStock());
            else if (null == price && null != stock)
                product = new Product(id,product.getProductName(), product.getPrice(), stock);
            else
                product = new Product(id,product.getProductName(), price, stock);
            productList.put(id, product);
        }
    }
}
