package com.micropos.carts.mapper;

import com.micropos.dto.ItemDto;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.Cart;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface CartMapper {

    Collection<ItemDto> toItemsDto(Collection<Item> items);

    Collection<Item> toItems(Collection<ItemDto> itemDtos);

    Item toItem(ItemDto productDto);

    ItemDto toItemDto(Item item);
}
