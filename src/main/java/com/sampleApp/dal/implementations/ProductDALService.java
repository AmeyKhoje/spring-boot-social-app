package com.sampleApp.dal.implementations;

import com.sampleApp.dal.interfaces.ProductDAL;
import com.sampleApp.dal.interfaces.UserDAL;
import com.sampleApp.models.Product;
import com.sampleApp.models.User;
import com.sampleApp.utils.DatabaseUtility;
import com.sampleApp.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Repository
public class ProductDALService implements ProductDAL {

  @Autowired
  private DatabaseUtility databaseUtility;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private UserDALService userDALService;

//  private ProductValidator productValidator;


  @Override
  public Product create(Product product) {
    Boolean isValidProduct = new ProductValidator().isValidProduct(product);
    if (isValidProduct == false) {
      return null;
    }
    if (
            databaseUtility.checkIfEntryExistsById(User.class, product.getCreatedBy(), "_id")
    ) {
      mongoTemplate.save(product);
      return product;
    }
    else {
      return null;
    }
  }

  @Override
  public Object delete(String productId) {
    if (
      databaseUtility.checkIfEntryExistsById(Product.class, productId, "productId")
    ) {
      Query query = new Query();
      query.addCriteria(Criteria.where("productId").is(productId));
      try {
        mongoTemplate.remove(query, Product.class);
        return "Product deleted";
      }
      catch (HttpClientErrorException.NotFound e) {
        throw e;
      }
    }
    else {
      return "Product not found";
    }
  }

  @Override
  public List<Product> getAllProducts() {
    return mongoTemplate.findAll(Product.class);
  }

  @Override
  public List<Product> getAllProductsCreatedByUser(String userId) {
    List<Product> products = null;
    if (
      databaseUtility.checkIfEntryExistsById(
        User.class, userId, "userId"
      )
    ) {
      Query query = new Query();
      query.addCriteria(Criteria.where("createdBy").is(userId));

      try {
        products = mongoTemplate.find(query, Product.class);
      }
      catch (Exception e) {
        throw e;
      }
    }
    return products;
  }

  @Override
  public Object getProductById(String productId) {
    Product product = null;

    if (
      databaseUtility.checkIfEntryExistsById(Product.class, productId, "productId")
    ) {
      Query query = new Query();
      query.addCriteria(Criteria.where("productId").is(productId));

      try {
        product = mongoTemplate.findOne(query, Product.class);
      }
      catch (Exception e) {
        throw e;
      }

      return product;
    }
    else {
      return "Product does not exist.";
    }
  }
}
