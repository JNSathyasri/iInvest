package com.ofss;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ofss.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>
{
	Admin findByUsername(String username);
}
