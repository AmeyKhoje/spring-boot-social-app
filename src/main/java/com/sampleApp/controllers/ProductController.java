package com.sampleApp.controllers;

import com.sampleApp.dal.implementations.ProductDALService;
import com.sampleApp.dal.interfaces.ProductDAL;
import com.sampleApp.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("products")
public class ProductController {
  private final ProductDAL productDAL;

  @Autowired
  public ProductController(ProductDALService productDALService) {
    this.productDAL = productDALService;
  }
  @PostMapping("/_new")
  public Product create(Product product) {
    return productDAL.create(product);
  }

  @DeleteMapping("/{productId}/_delete")
  public Object delete(@PathVariable String productId) {
    return productDAL.delete(productId);
  }

  @GetMapping("/_all")
  public List<Product> getAllProducts() {
    return productDAL.getAllProducts();
  }

  @GetMapping("/{userId}/_list")
  public List<Product> getAllProductsCreatedByLoggedInUser(@PathVariable String userId) {
    return productDAL.getAllProductsCreatedByLoggedInUser(userId);
  }

  @GetMapping("/{productId}")
  public Object getProductById(@PathVariable String productId) {
    return productDAL.getProductById(productId);
  }
}