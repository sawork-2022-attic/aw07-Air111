package com.micropos.carts.service;

import com.micropos.carts.model.Item;
import com.micropos.carts.repository.Cart;
import com.micropos.carts.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SplittableRandom;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    public CartServiceImpl(@Autowired CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Integer newCart() {
        return cartRepository.newCart();
    }

    @Override
    public List<Item> items(String userId) {
        return cartRepository.items(userId);
    }

    @Override
    public Item getItem(String userId, String productId) {
        return cartRepository.getItem(userId, productId);
    }

    @Override
    public boolean add(String userId, String productId, int amount) {
        return cartRepository.addItem(userId, productId, amount);
    }

    @Override
    public boolean remove(String userId, String productId) {
        return cartRepository.removeProduct(userId, productId);
    }

    public List<Item> remove(String userId)  {
        return cartRepository.remove(userId);
    }
}
