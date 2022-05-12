package com.micropos.delivery;

import com.micropos.carts.model.Item;
import com.micropos.dto.ItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class DeliveryApplication {

    public static final Logger log = LoggerFactory.getLogger(DeliveryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

    @Bean
    public Consumer<ItemDto> checkItem() { return new ItemChecker(); }
}
