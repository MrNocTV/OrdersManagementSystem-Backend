package com.smartscan.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartscan.db.model.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {

	public List<ItemImage> findByBarcode(String barcode);

	public Integer countByBarcode(String barcode);

	public ItemImage findByBarcodeAndFileName(String barcode, String fileName);

}
