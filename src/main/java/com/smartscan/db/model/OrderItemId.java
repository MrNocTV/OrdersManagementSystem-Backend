package com.smartscan.db.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderItemId implements Serializable {

	private static final long serialVersionUID = 1163347452811191867L;

	@Column(name = "order_code", length = 25)
	private String orderCode;

	@Column(name = "barcode", length = 25)
	private String barcode;

	public OrderItemId() {
		super();
	}

	public OrderItemId(String orderCode, String barcode) {
		super();
		this.orderCode = orderCode;
		this.barcode = barcode;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof OrderItemId))
			return false;
		OrderItemId that = (OrderItemId) o;
		System.out.println("*** compare 1" + this.orderCode.equals(that.orderCode));
		System.out.println("*** compare 2" + this.barcode.equals(that.barcode));
		return this.orderCode.equals(that.orderCode) && this.barcode.equals(that.barcode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.orderCode, this.barcode);
	}

	@Override
	public String toString() {
		return "OrderItemId [orderCode=" + orderCode + ", barcode=" + barcode + "]";
	}

}
