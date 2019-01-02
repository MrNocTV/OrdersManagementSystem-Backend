package com.smartscan.dto;

import java.util.Date;

public class OrderDTO {
	private String code;
	private String type;
	private String customer;
	private String status;
	private Double total;
	private Date createdDate;
	private String description;
	private String owner;
	private String checker;

	public OrderDTO() {
		super();
	}

	public OrderDTO(String code, String type, String customer, String status, Double total, Date createdDate,
			String description, String owner, String checker) {
		super();
		this.code = code;
		this.type = type;
		this.customer = customer;
		this.status = status;
		this.total = total;
		this.createdDate = createdDate;
		this.description = description;
		this.owner = owner;
		this.checker = checker;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Override
	public String toString() {
		return "OrderDTO [code=" + code + ", type=" + type + ", customer=" + customer + ", status=" + status
				+ ", total=" + total + ", createdDate=" + createdDate + ", description=" + description + ", owner="
				+ owner + ", checker=" + checker + "]";
	}

}
