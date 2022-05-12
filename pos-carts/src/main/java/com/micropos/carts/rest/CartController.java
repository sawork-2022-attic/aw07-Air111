package com.micropos.carts.rest;

import com.micropos.api.ApiUtil;
import com.micropos.api.CartApi;
import com.micropos.dto.ItemDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.Cart;
import com.micropos.carts.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class CartController implements CartApi {

    private final CartMapper cartMapper;

    private final CartService cartService;


    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<Integer> newCart() {
        Integer id = this.cartService.newCart();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto>> listCart(String userId) {
        List<ItemDto> items = new ArrayList<>(cartMapper.toItemsDto(this.cartService.items(userId)));
        if (items == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ItemDto> addToCart(String userId, String productId, Integer amount) {
        this.cartService.add(userId, productId, amount.intValue());
        ItemDto itemDto = cartMapper.toItemDto(this.cartService.getItem(userId, productId));
        if (itemDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto>> removeFromCart(String userId, String productId) {
        if (this.cartService.remove(userId, productId) == false) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        List<ItemDto> items = new ArrayList<>(cartMapper.toItemsDto(this.cartService.items(userId)));
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto>> checkoutCart(String userId) {
        List<Item> cart = this.cartService.checkout(userId);
        if (cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        List<ItemDto> items = new ArrayList<>(cartMapper.toItemsDto(cart));
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
