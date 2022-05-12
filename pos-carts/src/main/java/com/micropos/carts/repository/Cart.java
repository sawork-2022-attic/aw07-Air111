package com.micropos.carts.repository;

import com.micropos.carts.model.Item;
import io.swagger.models.auth.In;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Cart implements CartRepository {

    private List<List<Item>> carts = new ArrayList<>();

    private Set<Integer> cartIds = new HashSet<>();
    private int count = 0;

    @Override
    public Integer newCart() {
        if (cartIds.size() >= Integer.MAX_VALUE)
            return null;
        while (cartIds.contains(count)) {
            count += 1;
        }
        cartIds.add(count);
        while (carts.size() < count + 1)
            carts.add(new ArrayList<>());
        return count;
    }

    @Override
    @Cacheable(value = "carts", key = "#userId")
    public List<Item> items(String userId) {
        return carts.get(Integer.parseInt(userId));
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

    @Override
    @CacheEvict(value = "carts", key = "#userId")
    public List<Item> remove(String userId) {
        int id = Integer.parseInt(userId);
        if (!cartIds.contains(id))
            return null;
        List<Item> ret = carts.get(id);
        carts.remove(id);
        cartIds.remove(id);
        return ret;
    }

}
