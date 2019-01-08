package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.Unit;

public interface UnitService {

	public void createUnit(Unit unit);

	public void updateUnit(Unit unit);

	public void deleteUnit(Unit unit);

	public Unit findByName(String name);

	public List<Unit> findAll();

}
