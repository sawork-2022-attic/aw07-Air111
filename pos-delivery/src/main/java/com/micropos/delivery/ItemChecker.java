package com.micropos.delivery;

import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Item;
import com.micropos.dto.ItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

public class ItemChecker implements Consumer<ItemDto> {

    public static final Logger log = LoggerFactory.getLogger(ItemChecker.class);

    private StreamBridge streamBridge;


    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void accept(ItemDto itemDto) {
        log.info("Deliver {} product(s) with ID {}", itemDto.getQuantity(), itemDto.getProductId());
        streamBridge.send("item-approved", message(itemDto));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
