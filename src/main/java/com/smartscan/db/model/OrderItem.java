package com.smartscan.db.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_item")
public class OrderItem {

	@EmbeddedId
	@NotNull
	private OrderItemId id;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "price")
	private Double price;

	public OrderItem(Order order, Item item, Integer quantity, Double price) {
		this.id = new OrderItemId(order.getOrderCode(), item.getBarcode());
		this.quantity = quantity;
		this.price = price;
	}

	public OrderItem() {
		super();
	}

	public OrderItemId getId() {
		return id;
	}

	public void setId(OrderItemId id) {
		this.id = id;
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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof OrderItem))
			return false;
		OrderItem that = (OrderItem) o;
		return that.id.equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
