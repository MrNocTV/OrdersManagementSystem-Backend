package com.smartscan.db.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.Item;
import com.smartscan.db.repository.ItemRepository;
import com.smartscan.db.service.ItemService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public void createItem(Item item) {
		itemRepository.save(item);
	}

	@Override
	public void updateItem(Item item) {
		itemRepository.save(item);
	}

	@Override
	public void deleteItem(Item item) {
		itemRepository.delete(item);
	}

	@Override
	public Item findByBarcode(String barcode) {
		return itemRepository.findByBarcode(barcode);
	}

	@Override
	public Page<Item> find10(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

}
