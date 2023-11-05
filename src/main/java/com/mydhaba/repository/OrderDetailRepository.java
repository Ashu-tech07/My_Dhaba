package com.mydhaba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mydhaba.model.Order;


public interface OrderDetailRepository extends JpaRepository<Order, Integer>{
  
	List<Order> findAllByUserId(int userId);
}
