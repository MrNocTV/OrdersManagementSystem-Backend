package com.smartscan.api.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.Item;
import com.smartscan.db.model.Unit;
import com.smartscan.db.service.ItemService;
import com.smartscan.db.service.UnitService;
import com.smartscan.dto.ItemDTO;

@CrossOrigin
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

	@GetMapping(path = "/api/items")
	public ResponseEntity<?> getItems(@RequestParam("filter") String filter,
			@RequestParam("sortOrder") String sortOrder, @RequestParam("pageNumber") String pageNumber,
			@RequestParam("pageSize") String pageSize) {
		try {
			System.out.println("Filter " + filter);
			// page
			Page<Item> itemPage = itemService
					.find10(PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
			List<Item> itemList = new ArrayList<>(itemPage.getContent());
			// filter
			if (!filter.equals("")) {
				String lowerCase = filter.toLowerCase();
				itemList = itemList.stream().filter(item -> {
					return item.getBarcode().toLowerCase().contains(lowerCase)
							|| item.getDescription().toLowerCase().contains(lowerCase)
							|| item.getPriceIn().toString().contains(lowerCase)
							|| item.getPriceOut().toString().contains(lowerCase)
							|| item.getInStock().toString().contains(lowerCase);
				}).collect(Collectors.toList());
			}

			// sort
			if (sortOrder.equals("") || sortOrder.equals("desc")) {
				itemList.sort(new Comparator<Item>() {
					public int compare(Item i1, Item i2) {
						return i2.getId().compareTo(i1.getId());
					}
				});
			} else {
				itemList.sort(new Comparator<Item>() {
					public int compare(Item i1, Item i2) {
						return i1.getId().compareTo(i2.getId());
					}
				});
			}

			return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "api/items/count")
	public ResponseEntity<?> countItems() {
		try {
			Long count = itemService.countAllItems();
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
