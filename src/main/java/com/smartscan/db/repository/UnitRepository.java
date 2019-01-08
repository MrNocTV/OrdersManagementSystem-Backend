package com.smartscan.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartscan.db.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

	public Unit findByName(String name);
}
