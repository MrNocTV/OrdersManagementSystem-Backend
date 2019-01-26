package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.ItemImage;
import com.smartscan.db.repository.ItemImageRepository;
import com.smartscan.db.service.ItemImageService;

@Service
@Transactional
public class ItemImageServiceImpl implements ItemImageService {

	@Autowired
	private ItemImageRepository itemImageRepository;

	@Override
	public void createItemImage(ItemImage itemImage) {
		itemImageRepository.save(itemImage);
	}

	@Override
	public void deleteItem(ItemImage itemImage) {
		itemImageRepository.delete(itemImage);
	}

	@Override
	public List<ItemImage> findByBarcode(String barcode) {
		return itemImageRepository.findByBarcode(barcode);
	}

	@Override
	public Integer countImageOfItem(String barcode) {
		return itemImageRepository.countByBarcode(barcode);
	}

	@Override
	public ItemImage findByBarcodeAndFileName(String barcode, String fileName) {
		return itemImageRepository.findByBarcodeAndFileName(barcode, fileName);
	}

}
