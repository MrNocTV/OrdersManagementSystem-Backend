package com.smartscan.dto;

import com.smartscan.db.model.Item;

public class ItemDTO {
	private String barcode;
	private String description;
	private Integer inStock = 1;
	private Double priceIn;
	private Double priceOut;
	private String unit;
	private String imagePath;

	public ItemDTO() {
		super();
	}

	public ItemDTO(String barcode, String description, Integer inStock, Double priceIn, Double priceOut, String unit,
			String imagePath) {
		super();
		this.barcode = barcode;
		this.description = description;
		this.inStock = inStock;
		this.priceIn = priceIn;
		this.priceOut = priceOut;
		this.unit = unit;
		this.imagePath = imagePath;
	}

	public ItemDTO(Item item) {
		this(item.getBarcode(), item.getDescription(), item.getInStock(), item.getPriceIn(), item.getPriceOut(),
				item.getUnit().getName(), item.getImagePath());
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getInStock() {
		return inStock;
	}

	public void setInStock(Integer inStock) {
		this.inStock = inStock;
	}

	public Double getPriceIn() {
		return priceIn;
	}

	public void setPriceIn(Double priceIn) {
		this.priceIn = priceIn;
	}

	public Double getPriceOut() {
		return priceOut;
	}

	public void setPriceOut(Double priceOut) {
		this.priceOut = priceOut;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "ItemDTO [barcode=" + barcode + ", description=" + description + ", inStock=" + inStock + ", priceIn="
				+ priceIn + ", priceOut=" + priceOut + ", unit=" + unit + "]";
	}

}
