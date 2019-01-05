package com.smartscan.db.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartscan.db.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	public Order findByOrderCode(String code);

	public List<Order> findByCustomer_Name(String name);

	public List<Order> findByType_Name(String name);

	public List<Order> findByStatus_Name(String name);

	public List<Order> findByOwner_Username(String username, Pageable pageable);

	public List<Order> findByChecker_Username(String username);

	public Long countByOwner_Username(String username);

}
