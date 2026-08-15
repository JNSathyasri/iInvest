package com.ofss.model;

public class Investor {
	int Inv_id;
	String Email;
	String Password;
	Double AccountBalance;
	public Investor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Investor(int inv_id, String email, String password, Double accountBalance) {
		super();
		Inv_id = inv_id;
		Email = email;
		Password = password;
		AccountBalance = accountBalance;
	}
	public int getInv_id() {
		return Inv_id;
	}
	public void setInv_id(int inv_id) {
		Inv_id = inv_id;
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
	public Double getAccountBalance() {
		return AccountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		AccountBalance = accountBalance;
	}
	@Override
	public String toString() {
		return "Investor [Inv_id=" + Inv_id + ", Email=" + Email + ", Password=" + Password + ", AccountBalance="
				+ AccountBalance + "]";
	}

}
