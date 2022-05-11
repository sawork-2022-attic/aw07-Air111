package com.micropos.carts.repository;

import com.micropos.carts.model.Item;
import io.swagger.models.auth.In;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart implements CartRepository {

    private List<List<Item>> carts = new ArrayList<>();

    private Integer count = 0;

    @Override
    public Integer newCart() {
        if (count == Integer.MAX_VALUE)
            return null;
        count += 1;
        while (carts.size() < count)
            carts.add(new ArrayList<>());
        return count - 1;
    }

    @Override
    @Cacheable(value = "carts", key = "#userId")
    public List<Item> items(String userId) {
        return carts.get(Integer.valueOf(userId));
    }

    @Override
    public Item getItem(String userId, String productId) {
        for (Item item: items(userId)) {
            if (item.getProductId().equals(productId))
                return item;
        }
        return null;
    }

    @Override
    @CacheEvict(value = "carts", key = "#userId")
    public boolean addItem(String userId, String productId, int amount) {
        List<Item> itemList = items(userId);
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getProductId().equals(productId)) {
                int quantity = itemList.get(i).getQuantity();
                quantity += amount;
                if (quantity < 0)
                    return false;
                if (quantity == 0)
                    itemList.remove(i);
                else
                    itemList.get(i).setQuantity(quantity);
                return true;
            }
        }
        return itemList.add(new Item(productId, amount));
    }

    @Override
    @CacheEvict(value = "carts", key = "#userId")
    public boolean removeProduct(String userId, String productId) {
        List<Item> itemList = items(userId);
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getProductId().equals(productId)) {
                itemList.remove(i);
                return true;
            }
        }
        return false;
    }

}
