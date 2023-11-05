package com.mydhaba.model;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;

	private Integer orderAmount;
	private Date orderDate;
	private boolean status;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<OrderItem> orderItems;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Order(User user, Integer orderAmount, Date orderDate, boolean status, Set<OrderItem> orderItems) {
		super();
		this.user = user;
		this.orderAmount = orderAmount;
		this.orderDate = orderDate;
		this.status = status;
		this.orderItems = orderItems;
	}

	public Order(Integer orderId, User user, Integer orderAmount, Date orderDate, boolean status,
			Set<OrderItem> orderItems) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.orderAmount = orderAmount;
		this.orderDate = orderDate;
		this.status = status;
		this.orderItems = orderItems;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderAmount, orderDate, orderId, orderItems, status, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(orderAmount, other.orderAmount) && Objects.equals(orderDate, other.orderDate)
				&& Objects.equals(orderId, other.orderId) && Objects.equals(orderItems, other.orderItems)
				&& status == other.status && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "OrderDetail [orderId=" + orderId + ", user=" + user + ", orderAmount=" + orderAmount + ", orderDate="
				+ orderDate + ", status=" + status + ", orderItems=" + orderItems + "]";
	}

}
