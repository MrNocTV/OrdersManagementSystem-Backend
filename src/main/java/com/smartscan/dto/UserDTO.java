package com.smartscan.dto;

public class UserDTO {
	private String name;

	public UserDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + "]";
	}

}
