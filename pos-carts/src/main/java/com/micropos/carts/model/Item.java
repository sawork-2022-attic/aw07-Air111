package com.micropos.carts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class Item implements Serializable {

    private String productId;
    private int quantity;

    public Item() {
        this.productId = "";
        this.quantity = 0;
    }

    public Item(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
