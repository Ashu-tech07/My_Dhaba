package com.mydhaba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BaseController implements ErrorController{

	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	public String getErrorPath() {
		return "/error";
	}
	
	@ResponseBody
	@RequestMapping("/error")
	public String loadErrorPage() {
		return "Invalid URL!";
	}
	
	@GetMapping({ "/", "/login" })
	public String loadLoginPage() {
		logger.debug("loading login Page");
		return "login";
	}
	
	@GetMapping("/register")
	public String laodRegisterPage(Model map, HttpSession session) {
		session.removeAttribute("error");
		logger.debug("loading register Page");
		return "register";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard(Model map, HttpServletResponse res) {
		res = disableCache(res);
		logger.debug("loading admin Dashboard");
		return "adminDashboard";
	}

	@GetMapping("/user/dashboard")
	public String userDashboard(Model map, HttpServletResponse res) {
		res = disableCache(res);
		logger.debug("loading user Dashboard");
		return "userDashboard";
	}

	@GetMapping("/orderDetails")
	public String orderDetails(Model map, HttpServletResponse res) {
		res = disableCache(res);
		logger.debug("loading order Details Page");
		return "orderDetails";
	}

	@GetMapping("/menuOverview")
	public String menuOverview(Model map, HttpServletResponse res) {
		res = disableCache(res);
		logger.debug("loading menu overview page");
		return "menuOverview";
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
