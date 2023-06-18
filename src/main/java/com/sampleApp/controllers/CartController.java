package com.sampleApp.controllers;

import com.sampleApp.dal.implementations.CartDALService;
import com.sampleApp.dal.interfaces.CartDAL;
import com.sampleApp.models.Cart;
import com.sampleApp.models.internals.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("cart")
public class CartController {
  private final CartDAL cartDAL;

  @Autowired
  public CartController(CartDALService cartDALService) {
    this.cartDAL = cartDALService;
  }

  @PostMapping("/{userId}/_modify")
  public Cart create(@RequestBody CartItem cartItem, @PathVariable String userId) {
    return cartDAL.modifyCart(cartItem, userId);
  }

  @GetMapping("/{userId}/_get")
  public Cart getUserCart(@PathVariable String userId) {
    return cartDAL.getCartOfUser(userId);
  }

}
