package com.mydhaba.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mydhaba.CustomResponse;
import com.mydhaba.model.Order;

import com.mydhaba.repository.OrderDetailRepository;

@Service
public class OrderDetailService {

	private OrderDetailRepository orderDetailsRepository;
	
	public CustomResponse createOrder(Order order) {
		try {
			Order dbOrderDetail=orderDetailsRepository.save(order);
			return new CustomResponse("success",dbOrderDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse("Something went wrong!!",null);
		}
	}
	
	public CustomResponse getAllOrders() {
		List<Order> list=orderDetailsRepository.findAll();
		if(list.size()!=0) {
			return new CustomResponse("Success", list);
		}
		else {
			return new CustomResponse("No order found", null);
		}
	}
	
	public CustomResponse getOrderById(int id) {
		try {
			Optional<Order> optionalOrderDetail=orderDetailsRepository.findById(id);
			if(optionalOrderDetail.isPresent()) {
				return new CustomResponse("Success",optionalOrderDetail.get());
			}
			else {
				return new CustomResponse("No order found", null);
			}
			
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!!", null);
		}
	}
	
	public CustomResponse updateOrderStatus(int id) {
		try {
			Optional<Order> optionalOrder=orderDetailsRepository.findById(id);
			if(optionalOrder.isPresent()) {
				Order order=optionalOrder.get();
				order.setStatus(false);
				Order dbOrder=orderDetailsRepository.save(order);
				
				return new CustomResponse("success",dbOrder);
			}
			else {
				return new CustomResponse("Item not found for id "+id,null);
			}
			
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!!", null);
		}
	}
	
	public CustomResponse deleteOrderById(int id) {
		try {
			orderDetailsRepository.deleteById(id);
			return new CustomResponse("Deleted successfully ","id "+id); 
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!!", null);
		}
	}
	
	public CustomResponse getAllOrdersByUserId(int userId) {
		try {
			List<Order> list= orderDetailsRepository.findAllByUserId(userId);
			if(list.size()!=0) {
				return new CustomResponse("success", list);
			}
			else {
				return new CustomResponse("No order found", null);
			}
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!!", null);
		}
	}
}
