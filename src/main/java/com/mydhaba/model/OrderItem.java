package com.mydhaba.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orderitem")
public class OrderItem {

	@JsonIgnore
	@Id
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

	@Id
	@ManyToOne
	@JoinColumn(name = "itemId")
	@NonNull
	private Item item;

	@NonNull
	private Integer quantity;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public OrderItem(Order order, Item item, Integer quantity) {
		super();
		this.order = order;
		this.item = item;
		this.quantity = quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItem(Item item, int quantity2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, order, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(item, other.item) && Objects.equals(order, other.order)
				&& Objects.equals(quantity, other.quantity);
	}

	@Override
	public String toString() {
		return "OrderItem [order=" + order + ", menu=" + item + ", quantity=" + quantity + "]";
	}

}
