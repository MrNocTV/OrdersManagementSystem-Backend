package com.smartscan.dto;

import java.util.Date;

public class CustomerDTO {

	private String name;
	private String address;
	private Date createdDate;

	public CustomerDTO() {
		super();
	}

	public CustomerDTO(String name, String address, Date createdDate) {
		super();
		this.name = name;
		this.address = address;
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "CustomerDTO [name=" + name + ", address=" + address + ", createdDate=" + createdDate + "]";
	}

}
