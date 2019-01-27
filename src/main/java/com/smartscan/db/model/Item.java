package com.smartscan.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = -9156105759245094481L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "barcode", unique = true, length = 25)
	private String barcode;

	@Column(name = "description")
	private String description;

	@Column(name = "in_stock")
	private Integer inStock;

	@Column(name = "price_in")
	private Double priceIn;

	@Column(name = "price_out")
	private Double priceOut;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "last_modified_date")
	private Date lastModifiedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id")
	private Unit unit;

	public Item() {
		super();
	}

	public Item(String barcode, String description, Integer inStock, Double priceIn, Date createdDate,
			Date lastModifiedDate, Unit unit, Double priceOut) {
		super();
		this.barcode = barcode;
		this.description = description;
		this.inStock = inStock;
		this.priceIn = priceIn;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.unit = unit;
		this.priceOut = priceOut;
	}

	public Double getPriceOut() {
		return priceOut;
	}

	public void setPriceOut(Double priceOut) {
		this.priceOut = priceOut;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", barcode=" + barcode + ", description=" + description + ", inStock=" + inStock
				+ ", priceIn=" + priceIn + ", createdDate=" + createdDate + "]";
	}

}
