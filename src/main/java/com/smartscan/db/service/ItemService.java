package com.smartscan.db.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartscan.db.model.Item;

public interface ItemService {

	public void createItem(Item item);

	public void updateItem(Item item);

	public void deleteItem(Item item);

	public Item findByBarcode(String barcode);

	public Page<Item> find10(Pageable pageable);

}
