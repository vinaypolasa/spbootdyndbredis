package com.pvk.app.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.pvk.app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
     @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    public Product save( Product product){
        dynamoDBMapper.save(product);
        return product;
    }

    public Product getProductById(String pid){
        return dynamoDBMapper.load(Product.class, pid);
    }
    public String delete(String pid){
        Product product = dynamoDBMapper.load(Product.class,pid);
        dynamoDBMapper.delete(product);
         return "product deleted !";
    }

    public String update(String pid, Product product){
        dynamoDBMapper.save(product,
                new DynamoDBSaveExpression()
                .withExpectedEntry("pid",
                        new ExpectedAttributeValue(
                                new AttributeValue().withS(pid)
                        )));
        return "Product Updated !";
    }

    public PaginatedScanList<Product> findall() {
      return  dynamoDBMapper.scan(Product.class,new DynamoDBScanExpression());
    }
}
