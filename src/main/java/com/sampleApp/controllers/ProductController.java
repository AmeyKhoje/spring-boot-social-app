package com.sampleApp.controllers;

import com.sampleApp.dal.implementations.ProductDALService;
import com.sampleApp.dal.interfaces.ProductDAL;
import com.sampleApp.models.Product;
import com.sampleApp.utils.DatabaseUtility;
import io.micrometer.core.ipc.http.HttpSender;
import jakarta.servlet.http.HttpServletRequest;
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
  private DatabaseUtility databaseUtility;

  @Autowired
  public ProductController(ProductDALService productDALService) {
    this.productDAL = productDALService;
  }
  @PostMapping("/_new")
  public Product create(@RequestBody Product product) {
    return productDAL.create(product);
  }

  @DeleteMapping("/{productId}/_delete")
  public Object delete(@PathVariable String productId) {
    return productDAL.delete(productId);
  }

  @GetMapping("/_all")
  public List<Product> getAllProducts() {
    System.out.println("Ins");
    return productDAL.getAllProducts();
  }

  @GetMapping("/{userId}/_list")
  public List<Product> getAllProductsCreatedByLoggedInUser(
          HttpServletRequest request,
          @PathVariable String userId
  ) {
    boolean isValidUser =
            databaseUtility.checkSameUser(
                    userId,
                    request.getHeader("Authorization").substring(7)
            );

    if (isValidUser) {
      return productDAL.getAllProductsCreatedByUser(userId);
    }

    return null;

  }

  @GetMapping("/{productId}")
  public Object getProductById(@PathVariable String productId) {

    return productDAL.getProductById(productId);
  }
}