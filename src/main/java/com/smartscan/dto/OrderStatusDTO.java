package com.smartscan.dto;

public class OrderStatusDTO {

	private String name;
	private String description;

	public OrderStatusDTO() {
		super();
	}

	public OrderStatusDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "OrderStatusDTO [name=" + name + ", description=" + description + "]";
	}

}
