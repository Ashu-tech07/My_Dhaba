package com.mydhaba.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mydhaba.CustomResponse;
import com.mydhaba.model.Item;
import com.mydhaba.model.Order;
import com.mydhaba.model.OrderItem;
import com.mydhaba.model.User;
import com.mydhaba.service.MenuItemService;
import com.mydhaba.service.OrderDetailService;
import com.mydhaba.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
 
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	MenuItemService menuItemService;

	@Autowired
	OrderDetailService orderDetailsService;

	@Autowired
	UserService userService;
	
	@PostMapping("/addItem")
	public String addNewMenuItem(@RequestParam String itemName, @RequestParam int itemPrice, HttpSession session) {
		Item item = new Item(itemName, itemPrice);

		logger.debug("add new item request for item = " + item);

		CustomResponse response = menuItemService.addNewItem(item);
		if (response.getData() != null) {
			Item itemDb = (Item) response.getData();
			logger.debug("item added successfully! with id = " + itemDb.getItemId());
		} else {
			logger.error("Unable to add the item [ " + response.getMessage() + " ]");
		}
		session.setAttribute("addNewItemMsg", response.getMessage());
		return "redirect:/admin/menuOverview";
	}
	
	@GetMapping ("/delete/{id}")
	public String deleteMenuItem(@PathVariable int id, HttpSession session) {

		logger.debug("delete item request for id = " + id);

		CustomResponse response = menuItemService.deleteItem(id);

		if (response.getData() != null) {
			logger.debug("item deleted successfully with id =" + id);
		} else {
			logger.error("Unable to delete the item with id + " + id);
		}
		session.setAttribute("deleteMsg", response.getMessage());
		return "redirect:/admin/menuOverview";
	}
	
	@PostMapping("/modify/{id}")
	public String updateMenuItem(@PathVariable int id, @RequestBody Item menuItem) {

		logger.debug("modify item request for id = " + id + " and newItem = " + menuItem);

		CustomResponse response = menuItemService.updateItem(id, menuItem);

		if (response.getData() != null) {
			Item itemDb = (Item) response.getData();
			logger.debug("item details updated for id = " + itemDb.getItemId() + " as " + itemDb);
		} else {
			logger.error("Unable to update item details [ " + response.getMessage() + " ]");
		}
		return "redirect:/admin/menuOverview";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/menuOverview")
	public String getMenuOverview(HttpSession session, HttpServletResponse res) {
		CustomResponse response = menuItemService.getAllItems();
		session.removeAttribute("list");
		session.removeAttribute("error");
		if (response.getData() != null) {
			List<Item> list = (List<Item>) response.getData();
			session.setAttribute("list", list);
		} else {
			session.setAttribute("error", response.getMessage());
		}
		res = disableCache(res);
		return "menuOverview";
	}
	
	@GetMapping("/orderDetails")
	public String getOrderDetails(HttpSession session, HttpServletResponse res) {

		session.removeAttribute("list");
		session.removeAttribute("error");

		CustomResponse response = orderDetailsService.getAllOrders();
		if (response.getData() != null) {
			session.setAttribute("list", response.getData());
		} else {
			session.setAttribute("error", response.getMessage());
		}
		res = disableCache(res);
		return "orderDetails";
	}
	
	@GetMapping("/cancelOrder/{orderId}")
	public String cancelOrder(@PathVariable int orderId, RedirectAttributes redirectAttributes) {

		CustomResponse response = orderDetailsService.updateOrderStatus(orderId);
		if (response.getData() != null) {
			redirectAttributes.addFlashAttribute("success", response.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("error", response.getMessage());
		}
		return "redirect:/admin/orderDetails";
	}
	
	@GetMapping("/fetchItems/{orderId}")
	@ResponseBody
	public Set<OrderItem> fetchItems(@PathVariable int orderId, RedirectAttributes redirectAttributes) {

		CustomResponse response = orderDetailsService.getOrderById(orderId);

		if (response.getData() != null) {
			Order order = (Order) response.getData();
			Set<OrderItem> orderItems = order.getOrderItems();
			return orderItems;

		} else {
			redirectAttributes.addFlashAttribute("error", response.getMessage());
			return null;
		}
	}
	
	@ResponseBody
	public User fetchUserDetails(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	public HttpServletResponse disableCache(HttpServletResponse res) {
		// Set to expire far in the past.
		res.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		res.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		res.setHeader("Pragma", "no-cache");
		return res;
	}
	
}
