package com.pvk.app.controllers;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.pvk.app.entity.Product;
import com.pvk.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product")

    public List<Product> getProducts(){
        System.out.println("connecting to db");
        return productRepository.findall();
    }
    @PostMapping("/product")
    public Product saveProduct(@RequestBody Product product){
        return  productRepository.save(product);
    }

    @GetMapping("/product/{id}")
    @Cacheable (value = "product")
    public Product getProductById(@PathVariable("id") String pid){
        System.out.println("connecting to db");
        return  productRepository.getProductById(pid);
    }

    @PutMapping("/product/{id}")
    @CachePut(value = "product",key = "#id")
    public String updateProduct(@PathVariable("id") String pid,@RequestBody Product product){
        return productRepository.update(pid,product);
    }
    @DeleteMapping("/product/{id}")
    @CacheEvict(value = "product",allEntries = true)
    public String deleteProduct(@PathVariable("id") String pid){
        return productRepository.delete(pid);
    }
}
