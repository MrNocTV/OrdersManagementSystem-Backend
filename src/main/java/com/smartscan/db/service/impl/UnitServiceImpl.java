package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.Unit;
import com.smartscan.db.repository.UnitRepository;
import com.smartscan.db.service.UnitService;

@Service
@Transactional
public class UnitServiceImpl implements UnitService {

	@Autowired
	private UnitRepository unitRepository;

	@Override
	public void createUnit(Unit unit) {
		unitRepository.save(unit);
	}

	@Override
	public void updateUnit(Unit unit) {
		unitRepository.save(unit);
	}

	@Override
	public void deleteUnit(Unit unit) {
		unitRepository.delete(unit);
	}

	@Override
	public Unit findByName(String name) {
		return unitRepository.findByName(name);
	}

	@Override
	public List<Unit> findAll() {
		return unitRepository.findAll();
	}

}
