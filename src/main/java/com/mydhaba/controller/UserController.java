package com.mydhaba.controller;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mydhaba.CustomResponse;
import com.mydhaba.model.Item;
import com.mydhaba.model.Order;
import com.mydhaba.model.OrderItem;
import com.mydhaba.model.User;
import com.mydhaba.service.MenuItemService;
import com.mydhaba.service.OrderDetailService;
import com.mydhaba.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger= LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
    @Autowired
	private MenuItemService menuService;

    @Autowired
    private OrderDetailService orderService;
    
    @PostMapping("/registerNewUser")
    public String registerNewUser(@ModelAttribute User user, RedirectAttributes redirectAttributes, HttpSession session) {
    	user.setRole("user");
    	logger.debug("user registration request for : "+user);
    	
    	CustomResponse response=userService.addUser(user);
    	session.removeAttribute("error");
    	
    	if(response.getData()!=null) {
    		User userDb=(User) response.getData();
    		redirectAttributes.addFlashAttribute("user", userDb);
			session.setAttribute("registerMsg", "User Registered Successfully!");
			logger.debug("user registered successfully with userId = " + userDb.getId());
			return "redirect:/login";
    	}
    	else {
    		redirectAttributes.addFlashAttribute("error", response.getMessage());
			redirectAttributes.addFlashAttribute("user", user);
			logger.error("Unable to register the user. [" + response.getMessage() + "]");
			return "redirect:/register";
    	}
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
    	logger.debug("user login request for : username =" + user.getUsername());

		session.removeAttribute("error");
		
		CustomResponse response=userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(response.getData()!=null) {
			User userDb=(User) response.getData();
			session.setAttribute("user", userDb);
			logger.debug("user logged in successfully with userId = " + userDb.getId());
			if(user.getRole().equals("admin")) {
				logger.debug("user is ADMIN, redirecting to admin dashboard");
				return "redirect:/admin/dashboard";
			}
			else {
				session.setAttribute("userId", user.getId());
				logger.debug("user is USER, redirecting to user dashboard");
				return "redirect:/user/dashboard";
			}
		}
		else {
			session.setAttribute("error", response.getMessage());
			logger.error("Unable to login. [" + response.getMessage() + "]");
			return "redirect:/";
		}
    }
    
    @GetMapping("/placeOrder")
    public String loadPlaceOrder(HttpSession session, HttpServletResponse res) {
    	session.removeAttribute("list");
		session.removeAttribute("error");
		
		CustomResponse itemResponse=menuService.getAllItems();
		if(itemResponse.getData()!=null) {
			@SuppressWarnings("unchecked")
			List<Item> list=(List<Item>) itemResponse.getData();
			
			session.setAttribute("list", list);
		}else {
			session.setAttribute("error", itemResponse.getMessage());
    }
		res = disableCache(res) ;
		logger.debug("loading place new order page");
		return "placeOrder";
    
   }
    
    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam(name="item_checkbox") String itemIds[], @RequestParam int orderAmount, HttpServletRequest req, HttpSession session) {
    	Set<OrderItem> orderItems= new HashSet<OrderItem>();
    	
    	for(String id:itemIds) {
    		CustomResponse resItem = menuService.getItemById(Integer.parseInt(id));
			if (resItem.getData() != null) {

				Item item = (Item) resItem.getData();

				int quantity = Integer.parseInt(req.getParameter("itemQuantity_" + id));

				orderItems.add(new OrderItem(item, quantity)); //constructor
			}
    	}
    	User user = null;

		if (session.getAttribute("user") != null) {
			user = (User) session.getAttribute("user");
		}
		logger.debug("UserID : " + session.getAttribute("user"));

		Date orderDate = new Date(System.currentTimeMillis());

		boolean status = true;

		Order order = new Order(user, orderAmount, orderDate, status, orderItems);

		logger.debug("new order request with " + order);

		CustomResponse response = orderService.createOrder(order);

		if (response.getData() != null) {
			Order orderDb = (Order) response.getData();

			logger.debug("order placed successfully with id = " + orderDb.getOrderId());

			return "redirect:/user/orderSuccess";
		} else {
			logger.debug("Unable to place the order [" + response.getMessage() + "]");
			return "redirect:/user/placeOrder";
		}
    }
    
    @GetMapping("/orderSuccess")
	public String orderSuccess(HttpSession session, HttpServletResponse res) {
		res = disableCache(res);
		logger.debug("loading order success page");
		return "success";
	}
    
    @GetMapping("/cancelOrder/{id}")
	public String cancelOrder(@PathVariable int id, HttpSession session) {

		logger.debug("order cancel request for id : " + id);

		CustomResponse response = orderService.updateOrderStatus(id);

		session.removeAttribute("error");

		if (response.getData() != null) {
			logger.debug("order cancelled with id = " + id);
			session.setAttribute("success", response.getMessage());
		} else {
			logger.debug("Unable to cancel the order with id = " + id + " [ " + response.getMessage() + " ] ");
			session.setAttribute("error", response.getMessage());
		}
		return "redirect:/user/fetchAllOrders";
	}
    
    public String getAllOrders(HttpSession session, HttpServletResponse res ) {
    	if(session.getAttribute("user")==null) {
    		return  "redirect:/";
    	}
    	
    	User user=(User) session.getAttribute("user");
    	int userId=user.getId();
    	
    	session.removeAttribute("list");
    	session.removeAttribute("error");
    	
    	logger.debug("fetching all order for userId " + userId);
    	
    	CustomResponse response=orderService.getAllOrdersByUserId(userId);
    	if (response.getData() != null) {
			session.setAttribute("list", response.getData());
		} else {
			session.setAttribute("error", response.getMessage());
		}
		res = disableCache(res);

		logger.debug("loading the view previous orders page. status : " + response.getMessage());

		return "viewOrders";
    }
    
    @GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		logger.debug("user logged out successfully!");
		return "redirect:/";
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
