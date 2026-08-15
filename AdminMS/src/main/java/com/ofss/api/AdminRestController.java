package com.ofss.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ofss.model.Admin;
import com.ofss.model.InvestmentAdvisor;
import com.ofss.service.AdminService;

@RestController
public class AdminRestController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/api/admin/login", method=RequestMethod.POST)
    public ResponseEntity<String> adminLogin(@RequestBody Admin admin) 
	{
		return adminService.validateAdminLogin(admin);
    }
	
	@RequestMapping(value="api/admin/investment-advisors", method=RequestMethod.GET)
	public List<InvestmentAdvisor> fetchInvestmentAdvisors()
	{
		return adminService.fetchInvestmentAdvisors();
	}
	
	@RequestMapping(value="api/admin/investment-advisors", method=RequestMethod.POST)
	public ResponseEntity<String> addInvestmentAdvisor(@RequestBody InvestmentAdvisor advisor)
	{
		return adminService.addInvestmentAdvisor(advisor);
	}
	
	@RequestMapping(value="api/admin/investment-advisors/{username}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteInvestmentAdvisor(@PathVariable String username)
	{
		return adminService.deleteInvestmentAdvisor(username);
	}
}
