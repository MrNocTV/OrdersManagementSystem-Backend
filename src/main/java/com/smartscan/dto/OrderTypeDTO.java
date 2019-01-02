package com.smartscan.dto;

public class OrderTypeDTO {

	private String name;
	private String description;

	public OrderTypeDTO() {
		super();
	}

	public OrderTypeDTO(String name, String description) {
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
		return "OrderTypeDTO [name=" + name + ", description=" + description + "]";
	}

}
