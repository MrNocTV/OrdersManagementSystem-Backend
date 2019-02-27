package com.smartscan.api.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartscan.db.model.Item;
import com.smartscan.db.model.ItemImage;
import com.smartscan.db.model.Unit;
import com.smartscan.db.service.ItemImageService;
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

	@Autowired
	private ItemImageService itemImageService;

	private static final int IMG_WIDTH = 302;
	private static final int IMG_HEIGHT = 168;

	@PostMapping(path = "/api/items")
	public ResponseEntity<?> addItem(@RequestBody ItemDTO itemDTO) {
		try {
			Unit unit = unitService.findByName(itemDTO.getUnit().trim());
			if (unit == null && !itemDTO.getUnit().trim().equals("")) {
				unit = new Unit(itemDTO.getUnit().trim(), "");
				unitService.createUnit(unit);
			}
			Item item = new Item(itemDTO.getBarcode(), itemDTO.getDescription(), itemDTO.getInStock(),
					itemDTO.getPriceIn(), new Date(), new Date(), unit, itemDTO.getPriceOut());
			if (itemService.findByBarcode(item.getBarcode()) != null) {
				throw new Exception("Duplicated item");
			}
			itemService.createItem(item);
			System.out.println(itemDTO);
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/api/items/update")
	public ResponseEntity<?> updateItem(@RequestBody ItemDTO itemDTO) {
		try {
			Unit unit = unitService.findByName(itemDTO.getUnit());
			Item item = itemService.findByBarcode(itemDTO.getBarcode());
			if (item == null) {
				return new ResponseEntity<String>("[BAD REQUEST] = Item does not exist", HttpStatus.NOT_FOUND);
			}
			item.setDescription(itemDTO.getDescription());
			item.setInStock(itemDTO.getInStock());
			item.setPriceIn(itemDTO.getPriceIn());
			item.setPriceOut(itemDTO.getPriceOut());
			item.setUnit(unit);
			item.setLastModifiedDate(new Date());
			itemService.updateItem(item);
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/api/items/image")
	public ResponseEntity<?> addItemImage(@RequestParam("uploads[]") MultipartFile[] files,
			@RequestParam("barcode") String barcode) {
		try {
			System.out.println("[====== length " + files.length);
			for (int i = 0; i < files.length; ++i) {
				byte[] imageInBytes = files[i].getBytes();
				String extension = FilenameUtils.getExtension(files[i].getOriginalFilename());
				String fileName = barcode + "_" + i + "." + extension;
				ItemImage itemImage = new ItemImage(imageInBytes, barcode, fileName);
				itemImageService.createItemImage(itemImage);
			}
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("FAILED", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/items/images/{barcode}")
	public ResponseEntity<?> getImageFileNameOfItem(@PathVariable String barcode) {
		try {
			List<ItemImage> itemImages = itemImageService.findByBarcode(barcode);
			List<String> itemImageFileNames = itemImages.stream().map(ItemImage::getFileName)
					.collect(Collectors.toList());
			if (itemImages.size() > 0)
				return new ResponseEntity<>(Collections.singletonMap("fileNames", itemImageFileNames), HttpStatus.OK);
			return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping(path = "/api/items/image/get/{file_name}")
	public ResponseEntity<InputStreamResource> getItemImage(@PathVariable("file_name") String fileName) {
		try {
			String barcode = fileName.split("\\.")[0];
			String extn = FilenameUtils.getExtension(fileName);
			barcode = barcode.split("_")[0];
			ItemImage itemImage = itemImageService.findByBarcodeAndFileName(barcode, fileName);
			BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(itemImage.getImage()));
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage thumbnail = resizeImage(originalImage, type);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(thumbnail, extn, baos);
			baos.flush();
			byte[] thumbnailInByte = baos.toByteArray();

			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
					.body(new InputStreamResource(new ByteArrayInputStream(thumbnailInByte)));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping(path = "/api/items/get/{barcode}")
	public ResponseEntity<?> getItem(@PathVariable String barcode) {
		try {
			Item item = itemService.findByBarcode(barcode);
			if (item == null) {
				throw new Exception("Item " + barcode + " does not exist");
			}
			ItemDTO itemDTO = new ItemDTO(item);
			return new ResponseEntity<ItemDTO>(itemDTO, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
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
			if (sortOrder.equals("") || sortOrder.equals("asc")) {
				itemList.sort(new Comparator<Item>() {
					public int compare(Item i1, Item i2) {
						return i1.getId().compareTo(i2.getId());
					}
				});
			} else {
				itemList.sort(new Comparator<Item>() {

					public int compare(Item i1, Item i2) {
						return i2.getId().compareTo(i1.getId());
					}
				});
			}

			return new ResponseEntity<List<ItemDTO>>(itemList.stream().map(ItemDTO::new).collect(Collectors.toList()),
					HttpStatus.OK);
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

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}
}
