package com.micropos.order.rest;

import com.micropos.dto.ItemDto;
import com.micropos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.micropos.api.OrderApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class OrderController implements OrderApi {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Integer> checkout(List<ItemDto> itemsDto) {
        orderService.checkout(itemsDto);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
