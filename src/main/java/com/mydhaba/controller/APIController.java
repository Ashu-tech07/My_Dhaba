package com.mydhaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydhaba.CustomResponse;
import com.mydhaba.model.Item;
import com.mydhaba.service.MenuItemService;
import com.mydhaba.service.OrderDetailService;
import com.mydhaba.service.UserService;

@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	MenuItemService menuItemService;

	@Autowired
	OrderDetailService orderDetailsService;

	@Autowired
	UserService userService;

	@GetMapping("/user")
	public CustomResponse getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/menuItem")
	public CustomResponse getMenuItem() {
		return menuItemService.getAllItems();
	}

	@GetMapping("/menuItem/{id}")
	public CustomResponse getMenuItemById(@PathVariable int id) {
		return menuItemService.getItemById(id);
	}

	@PutMapping("/menuItem/{id}")
	public CustomResponse updateItem(@PathVariable int id, @RequestBody Item item) {
		return menuItemService.updateItem(id, item);
	}

	@PostMapping("/menuItem")
	public CustomResponse addNewMenuItem(@RequestBody Item item) {
		return menuItemService.addNewItem(item);
	}

	@DeleteMapping("/menuItem/{id}")
	public CustomResponse deleteMenuItem(@PathVariable int id) {
		return menuItemService.deleteItem(id);
	}

	@GetMapping("/order")
	public CustomResponse getAllOrderDetails() {
		return orderDetailsService.getAllOrders();
	}

	@GetMapping("/order/{id}")
	public CustomResponse getOrderDetails(@PathVariable int id) {
		return orderDetailsService.getOrderById(id);
	}

	@DeleteMapping("/order/{id}")
	public CustomResponse deleteOrderById(@PathVariable int id) {
		return orderDetailsService.deleteOrderById(id);
	}
}
