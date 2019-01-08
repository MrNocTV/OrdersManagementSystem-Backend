package com.smartscan.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartscan.db.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	public Item findByBarcode(String barcode);

	public Page<Item> findAll(Pageable pageable);

}
