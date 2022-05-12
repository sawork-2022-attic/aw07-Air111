package com.micropos.carts.service;

import com.micropos.carts.model.Item;
import com.micropos.carts.repository.Cart;

import java.util.List;

public interface CartService {

    public Integer newCart();

    public Item getItem(String userId, String productId);

    public List<Item> items(String userId);

    public boolean add(String userId, String productId, int amount);

    public List<Item> remove(String userId);

    public boolean remove(String userId, String productId);
}
