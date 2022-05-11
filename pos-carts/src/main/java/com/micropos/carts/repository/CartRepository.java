package com.micropos.carts.repository;


import com.micropos.carts.model.Item;

import java.util.List;

public interface CartRepository {

    public Integer newCart();

    public List<Item> items(String userId);

    public Item getItem(String userId, String productId);

    public boolean addItem(String userId, String productId, int amount);

    public boolean removeProduct(String userId, String productId);

}