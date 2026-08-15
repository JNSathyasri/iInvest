package com.ofss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ofss.model.Admin;
import com.ofss.model.InvestmentAdvisor;
import com.ofss.service.AdminService;

@Controller
public class AdminController{
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/admin/login", method=RequestMethod.GET)
    public String showAdminLogin() {
		System.out.println("hi this is get admin-login");
        return "admin_login"; // Returns admin login view
    }
	
	@RequestMapping(value="/admin/login", method=RequestMethod.POST)
	public String login(@RequestParam("username") String username,
	                    @RequestParam("password") String password,
	                    Model model) {
		System.out.println("hi this is post admin-login");
	    Admin admin = new Admin(username, password);
	    ResponseEntity<String> response = adminService.validateAdminLogin(admin);

	    if (response.getStatusCode() == HttpStatus.OK) {
	        // Redirect on successful login
	        return "redirect:/admin/portal";
	    } else {
	        // Add error message from ResponseEntity body to the model
	        model.addAttribute("error", response.getBody());
	        // Return to the login page with the error message
	        return "admin_login";
	    }
	}

	
	@RequestMapping(value="/admin/portal", method=RequestMethod.GET)
	public String showAdminDashboard(Model model)
	{
		System.out.println("hi this is get admin-portal");
		List<InvestmentAdvisor> advisors = adminService.fetchInvestmentAdvisors();
		model.addAttribute("advisors", advisors);
		return "admin_portal"; // Returns the admin dashboard view
	}
	
	@RequestMapping(value="/admin/portal", method=RequestMethod.POST)
	public String updateCredentials(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model)
	{
		System.out.println("hi this is post admin-portal");
		Admin admin = new Admin(username, password);
	    ResponseEntity<String> response = adminService.updateCredentials(admin);
	    
	    if (response.getStatusCode() == HttpStatus.OK){
	        model.addAttribute("status", "success");
	        model.addAttribute("message", "Credentials updated successfully.");
	    } else {
	        model.addAttribute("status", "error");
	        model.addAttribute("message", "Failed to update credentials. Please try again.");
	    }

	    // Return to the same JSP file, now with the message
	    List<InvestmentAdvisor> advisors = adminService.fetchInvestmentAdvisors();
		model.addAttribute("advisors", advisors);
		return "admin_portal"; // Returns the admin dashboard view
	}
	
	@RequestMapping(value="/admin/portal/addAdvisor", method=RequestMethod.POST)
	public String addAdvisor(@RequestParam("id") long id, @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model)
	{
		System.out.println("in the controller post add");
		System.out.println(id + username + password);
		InvestmentAdvisor advisor = new InvestmentAdvisor(id,username,password);
		System.out.println("advisor after declaring" + advisor);
		ResponseEntity<String> response = adminService.addInvestmentAdvisor(advisor);
		System.out.println(response);
		if(response.getBody().equals("Username already exists."))
		{
			model.addAttribute("statusAdvisor", "error");
	        model.addAttribute("messageAdvisor", "Username already exists.");
		}
		else
		{
			model.addAttribute("statusAdvisor", "success");
	        model.addAttribute("messageAdvisor", "Credentials updated successfully.");
		}
		List<InvestmentAdvisor> advisors = adminService.fetchInvestmentAdvisors();
		model.addAttribute("advisors", advisors);
		return "admin_portal"; // Returns the admin dashboard view
	}
	
	@RequestMapping(value="/admin/portal/deleteAdvisor", method=RequestMethod.POST)
	public String deleteAdvisor(@RequestParam("advisorUsername") String username,
            Model model)
	{
		ResponseEntity<String> response = adminService.deleteInvestmentAdvisor(username);
		
		if(response.getStatusCode() == HttpStatus.OK)
		{
			model.addAttribute("statusAdvisor", "success");
	        model.addAttribute("messageAdvisor", "Deleted Successfully.");
		}
		else
		{
			model.addAttribute("statusAdvisor", "error");
	        model.addAttribute("messageAdvisor", response.getBody());
		}
		List<InvestmentAdvisor> advisors = adminService.fetchInvestmentAdvisors();
		model.addAttribute("advisors", advisors);
		return "admin_portal"; // Returns the admin dashboard view
	}
}
