package com.ofss.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ofss.AdminRepository;
import com.ofss.model.Admin;
import com.ofss.model.InvestmentAdvisor;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	RestTemplate rt;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public ResponseEntity<String> validateAdminLogin(Admin admin) {
		Admin temp = adminRepository.findByUsername(admin.getUsername());
//		String pass = passwordEncoder.encode(temp.getPassword());
//		temp.setPassword(pass);
//		adminRepository.save(temp);
		if (temp != null) {
			if (passwordEncoder.matches(admin.getPassword(), temp.getPassword())) {
				// Login success, return 200 OK with message
				return ResponseEntity.ok("Login successful");
			}
			// Invalid password, return 401 Unauthorized with message
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Password.");
		}
		// Invalid username, return 404 Not Found with message
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Username.");
	}

	public ResponseEntity<String> updateCredentials(Admin admin) {

		// since if we get to this page and api means we are already inside the portal
		// so now.
		Admin temp = adminRepository.getReferenceById(1);
		if (temp != null) {
			temp.setUsername(admin.getUsername());
			temp.setPassword(passwordEncoder.encode(admin.getPassword()));
			adminRepository.save(temp);
			return ResponseEntity.ok("Updated successfully");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update credentials.");
	}

	public List<InvestmentAdvisor> fetchInvestmentAdvisors() {
		// TODO Auto-generated method stub
		String serviceURL = "http://INVESTMENTADVISORMS/investmentAdvisors";
		InvestmentAdvisor[] advisorsArray = rt.getForObject(serviceURL, InvestmentAdvisor[].class);
		return Arrays.asList(advisorsArray);
	}
	
	public ResponseEntity<String> addInvestmentAdvisor(InvestmentAdvisor advisor)
	{
		String serviceURL = "http://INVESTMENTADVISORMS/api/investment-advisors";
		ResponseEntity<String> response = rt.postForEntity(serviceURL, advisor, String.class);
		return response;
	}
	
	public ResponseEntity<String> deleteInvestmentAdvisor(String username)
	{
		try
		{
			String serviceURL = "http://INVESTMENTADVISORMS/api/investment-advisors/" + username;
			rt.delete(serviceURL);
			return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
		}
		catch (HttpClientErrorException e) {
	        // Handle the 4xx errors
	        String errorMessage = e.getResponseBodyAsString();
	        return ResponseEntity.status(e.getStatusCode()).body(errorMessage);
	
	    } catch (Exception e) {
	        // Handle other exceptions
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An unexpected error occurred while deleting the Investment Advisor.");
	    }
	}

}
