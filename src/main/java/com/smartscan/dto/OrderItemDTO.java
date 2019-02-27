package com.smartscan.dto;

public class OrderItemDTO {

	private String orderCode;
	private String barcode;
	private Integer quantity;
	private Double price;
	private String description;
	private String unit;
	private boolean checked;

	public OrderItemDTO() {
		super();
	}

	public OrderItemDTO(String orderCode, String barcode, Integer quantity, Double price, boolean checked) {
		super();
		this.orderCode = orderCode;
		this.barcode = barcode;
		this.quantity = quantity;
		this.price = price;
		this.checked = checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean getChecked() {
		return checked;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderItemDTO [orderCode=" + orderCode + ", barcode=" + barcode + ", quantity=" + quantity + ", price="
				+ price + "]";
	}

}
