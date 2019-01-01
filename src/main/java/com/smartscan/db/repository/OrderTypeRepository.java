package com.smartscan.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartscan.db.model.OrderType;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Integer> {

	public OrderType findByName(String name);

}
