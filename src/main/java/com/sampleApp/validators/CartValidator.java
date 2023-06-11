package com.sampleApp.validators;

import com.sampleApp.models.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CartValidator {

  public Boolean isValidCart(Cart cart) throws Exception {
    List<Error> errors = new ArrayList<>();
    if (cart.isEmpty()) {
      return false;
    }

    validateCart(cart, errors);

    if (errors.stream().count() > 0) {
      return false;
    }
    return true;
  }

  private static void validateCart(Cart cart, List<Error> errors) {
    Optional.ofNullable(cart.getUserId()).ifPresentOrElse(id -> {}, () -> errors.add(new Error("User id is required for creating cart or adding items to cart")));

    Optional.ofNullable(cart.getItems()).ifPresentOrElse(items -> {
      AtomicInteger count = new AtomicInteger();
      items.stream().forEach(item -> {

        Optional.ofNullable(item.getProductId()).ifPresentOrElse(pId -> {}, () -> {
          count.set(count.get() + 1);
        });

        Optional.ofNullable(item.getQuantity()).ifPresentOrElse(c -> {}, () -> {
          count.set(count.get() + 1);
        });
      });

      if (count.get() > 0) {
        errors.add(new Error("Cart Items provided are missing productId or quantity"));
      }
    }, () -> errors.add(new Error("Items/Item is required to create/edit cart")));

  }
}
