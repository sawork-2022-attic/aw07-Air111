package com.micropos.carts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.Cart;
import com.micropos.carts.repository.CartRepository;
import com.micropos.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartMapper cartMapper;
    private CartRepository cartRepository;

    private RestTemplate restTemplate;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    @Override
    public List<Item> checkout(String userId)  {
        List<Item> cart = cartRepository.remove(userId);
        if (cart != null) {
            ObjectMapper mapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = null;
            try {
                request = new HttpEntity<>(mapper.writeValueAsString(cart), headers);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            restTemplate.postForObject("http://localhost:8080/api/order/checkout", request, Integer.class);
        }
        return cart;
    }
}
