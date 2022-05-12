package com.micropos.order.service;

import com.micropos.carts.mapper.CartMapper;
import com.micropos.dto.ItemDto;
import com.micropos.carts.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan("com.micropos.order,com.micropos.carts")
public class OrderServiceImpl implements OrderService {

    public static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void checkout(List<ItemDto> itemsDto) {
        for (ItemDto itemDto: itemsDto) {
            log.info("Checkout {} product(s) with ID {}", itemDto.getQuantity(), itemDto.getProductId());
            streamBridge.send("ItemDeliverer", itemDto);
        }
    }
}
