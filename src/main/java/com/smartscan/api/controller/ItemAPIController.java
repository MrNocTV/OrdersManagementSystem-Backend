package com.smartscan.api.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.Item;
import com.smartscan.db.model.Unit;
import com.smartscan.db.service.ItemService;
import com.smartscan.db.service.UnitService;
import com.smartscan.dto.ItemDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class ItemAPIController {

	private static final Logger logger = LoggerFactory.getLogger(ItemAPIController.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private UnitService unitService;

	@PostMapping(path = "/api/item")
	public ResponseEntity<?> addItem(@RequestBody ItemDTO itemDTO) {
		try {
			Unit unit = unitService.findByName(itemDTO.getUnit());
			Item item = new Item(itemDTO.getBarcode(), itemDTO.getDescription(), itemDTO.getInStock(),
					itemDTO.getPriceIn(), new Date(), new Date(), unit, itemDTO.getPriceOut());
			if (itemService.findByBarcode(item.getBarcode()) != null) {
				throw new Exception("Duplicated item");
			}
			itemService.createItem(item);
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/item/{page}")
	public ResponseEntity<?> getOrdersOfUser(@PathVariable("page") Integer page) {
		try {
			Page<Item> tenItems = itemService.find10(PageRequest.of(page, 10));
			
			return new ResponseEntity<List<Item>>(tenItems.getContent(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
