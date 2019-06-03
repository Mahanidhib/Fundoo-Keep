package com.bridgelabz.app.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.app.model.LoginRequest;
import com.bridgelabz.app.model.User;

public interface UserService {

	public String login(User user);

	public User update(String token, User user);

	public User userRegistration(User user, HttpServletRequest request);

	public String encryptedPassword(String password);

	public String login(LoginRequest loginReq);

	public boolean delete(String token);

	public User getUserInfoByEmail(String email);

	public Optional<User> findById(int id);

	public List<User> fetchData();
	// public String sendMail(User userInfo, String forgotPasswordUrl, String
	// subject);

}
