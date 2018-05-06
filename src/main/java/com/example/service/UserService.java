package com.example.service;

import com.example.model.Users;

public interface UserService {
	public Users findUserByEmail(String email);
	public void saveUser(Users user);
}
