package com.example.accesa_application.service;

import com.example.accesa_application.domain.Item;
import com.example.accesa_application.repository.IItemRepository;

import java.util.Properties;

public class ItemService implements IItemService<Integer>{
    Properties props ;
    private final IItemRepository<Integer> itemRepository;

    public ItemService(Properties props,IItemRepository<Integer> itemRepository) {
        this.props = props;
        this.itemRepository = itemRepository;
    }
    @Override
    public void add(Item<Integer> elem) {
        itemRepository.add(elem);
    }

    @Override
    public void update(Integer id, Item<Integer> elem) {
        itemRepository.update(id,elem);
    }

    @Override
    public void delete(Integer id) {
        itemRepository.delete(id);

    }

    @Override
    public Item<Integer> findAfterId(Integer id) {
        return itemRepository.findAfterId(id);
    }

    @Override
    public Iterable<Item<Integer>> getAll() {
        return itemRepository.getAll();
    }
}
