package com.sampleApp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

  USER_READ("user:read"),
  USER_UPDATE("user:update"),
  USER_CREATE("user:create"),
  USER_ADD_TO_CART("user:add_to_cart"),
  USER_REMOVE_FROM_CART("user:remove_from_cart");

  @Getter
  private final String permission;
}
