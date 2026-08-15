package com.ofss.model;

public class InvestmentAdvisor {
	Long IAid;
	String Email;
	String Password;
	public InvestmentAdvisor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvestmentAdvisor(Long iAid, String email, String password) {
		super();
		IAid = iAid;
		Email = email;
		Password = password;
	}
	public Long getIAid() {
		return IAid;
	}
	public void setIAid(Long iAid) {
		IAid = iAid;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	@Override
	public String toString() {
		return "InvestmentAdvisor [IAid=" + IAid + ", Email=" + Email + ", Password=" + Password + "]";
	}
	
	
	
}
