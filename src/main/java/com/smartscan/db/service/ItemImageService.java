package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.ItemImage;

public interface ItemImageService {

	public void createItemImage(ItemImage itemImage);

	public void deleteItem(ItemImage itemImage);

	public List<ItemImage> findByBarcode(String barcode);

	public Integer countImageOfItem(String barcode);

	public ItemImage findByBarcodeAndFileName(String barcode, String fileName);
}
