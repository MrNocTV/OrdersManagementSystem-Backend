package com.smartscan.api.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

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

	private static final int IMG_WIDTH = 337;
	private static final int IMG_HEIGHT = 168;

	@PostMapping(path = "/api/items")
	public ResponseEntity<?> addItem(@RequestBody ItemDTO itemDTO) {
		try {
			Unit unit = unitService.findByName(itemDTO.getUnit());
			Item item = new Item(itemDTO.getBarcode(), itemDTO.getDescription(), itemDTO.getInStock(),
					itemDTO.getPriceIn(), new Date(), new Date(), unit, itemDTO.getPriceOut(), itemDTO.getImagePath());
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

	@PostMapping(path = "/api/items/image")
	public ResponseEntity<?> addItemImage(@RequestParam("file") MultipartFile file,
			@RequestParam("barcode") String barcode) {
		try {
			System.out.println("File " + file.getOriginalFilename());
			System.out.println("barcode " + barcode);
			String[] split = file.getOriginalFilename().split("\\.");
			String extension = split[split.length - 1];
			String imagePath = barcode + "." + extension;
			System.out.println("Image path " + imagePath);
			URL itemImageFolder = ItemAPIController.class.getClassLoader().getResource("item_image/");
			System.out.println("===== itemImageFolder: " + itemImageFolder.getPath() + imagePath);
			Path rootLocation = Paths.get(itemImageFolder.getPath());
			if (Files.notExists(rootLocation)) {
				Files.createDirectories(rootLocation);
			}
			Files.copy(file.getInputStream(), Paths.get(itemImageFolder.getPath() + imagePath),
					StandardCopyOption.REPLACE_EXISTING);
			// create thumbs nail
			BufferedImage originalImage = ImageIO.read(new File(itemImageFolder.getPath() + imagePath));
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizeImageJpg = resizeImage(originalImage, type);
			System.out.println(
					"====== Thumps nail created " + itemImageFolder.getPath() + barcode + "_thumbsnail." + extension);

			ImageIO.write(resizeImageJpg, extension,
					new File(itemImageFolder.getPath() + barcode + "_thumbsnail." + extension));
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("FAILED", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/items/image/get/{file_name}")
	public ResponseEntity<InputStreamResource> getItemImage(@PathVariable("file_name") String fileName) {
		// append "_thumbsnail" to file name
		fileName = fileName.split("\\.")[0] + "_thumbsnail." + fileName.split("\\.")[1];
		System.out.println("====== READING RESOURCE " +ItemAPIController.class.getClassLoader().getResource("item_image/").getPath() + fileName);
		InputStream resourceAsStream = ItemAPIController.class.getClassLoader().getResourceAsStream("item_image/" + fileName);

		// ClassPathResource imgFile = new ClassPathResource("item_image/" + fileName,
		// getClass().getClassLoader());
		try {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(resourceAsStream));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
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
