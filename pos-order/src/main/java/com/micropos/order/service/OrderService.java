package com.micropos.order.service;

import com.micropos.dto.ItemDto;

import java.util.List;

public interface OrderService {

    public void checkout(List<ItemDto> itemsDto);
}
