package com.bridgelabz.app.controller;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.LoginRequest;
import com.bridgelabz.app.model.User;
import com.bridgelabz.app.service.UserService;
import com.bridgelabz.app.util.JsonToken;

@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {
	@Autowired
	UserService userService;

	@Autowired
	private JavaMailSender sender;

	// SEND EMAIL
	@RequestMapping("/sendMail")
	public String sendMail(@RequestBody User user) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(user.getEmail());
			helper.setText("Greetings :)");
			helper.setSubject("Mail From Spring Boot");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginReq, HttpServletRequest request,
			HttpServletResponse response) {
		String token = userService.login(loginReq);
		 response.setHeader("token", token);
		if (token != null) {
			response.setHeader("token", token);
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{invalid user}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public void updateuser(@RequestBody User user, HttpServletRequest request) {
		System.out.println("I am  token at update method :" + request.getHeader("token"));
		userService.update(request.getHeader("token"), user);
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.DELETE)
	public void deleteuser(HttpServletRequest request) {

		System.out.println("I am  token at delete method :" + request.getHeader("token"));
		boolean b = userService.delete(request.getHeader("token"));
		System.out.println("-->" + b);

	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String forgotpassword(@RequestBody User user, HttpServletRequest request) {
		User userInfo = userService.getUserInfoByEmail(user.getEmail());

		if (userInfo != null) {
			String token = JsonToken.jwtToken("secretKey", userInfo.getId());

			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordUrl = forgotPasswordUrl + "/resetpassword/" + "token=" + token;
			System.out.println(forgotPasswordUrl);
			String subject = "FOR FORGOT PASSWORD";

			// userService.sendMail(userInfo, forgotPasswordUrl,subject);
			return "Mail Sent Successfully";
		} else
			return "not sent";
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public void resetPassword(@RequestBody User user, HttpServletRequest request) {
		// User userInfo=userService.getUserInfoByEmail(user.getEmail());
		int id = JsonToken.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userService.findById(id);
			User usr = userinfo.get();
			usr.setPassword(user.getPassword());
			/* usr.setStatus("1"); */
			userService.update(request.getHeader("token"), usr);
		}

	}
	/*
	 * @RequestMapping(value = "/sendtomail", method = RequestMethod.POST) public
	 * String sendtomail(@RequestBody User user, HttpServletRequest request) { User
	 * userInfo = userService.getUserInfoByEmail(user.getEmail());
	 * 
	 * if (userInfo != null) { String token = userService.jwtToken("secretKey",
	 * userInfo.getId());
	 * 
	 * StringBuffer requestUrl = request.getRequestURL();
	 * System.out.println(requestUrl); String forgotPasswordUrl =
	 * requestUrl.substring(0, requestUrl.lastIndexOf("/")); forgotPasswordUrl =
	 * forgotPasswordUrl + "/activestatus/" +"token="+ token;
	 * System.out.println(forgotPasswordUrl); String subject="Active Status";
	 * 
	 * userService.sendMail(userInfo, forgotPasswordUrl,subject); return
	 * "Mail Sent Successfully"+userInfo; } else return "Not Sent"; }
	 */

	@RequestMapping(value = "/activestatus", method = RequestMethod.PUT)
	public void activestatus(HttpServletRequest request) {
		// User userInfo=userService.getUserInfoByEmail(user.getEmail());
		int id = JsonToken.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userService.findById(id);
			User usr = userinfo.get();
			usr.setStatus("1");
			userService.update(request.getHeader("token"), usr);
		}
	}

	@RequestMapping(value = "/fetchData", method = RequestMethod.GET)
	public List<User> featchUser(HttpServletRequest request) {

		return userService.fetchData();
	}
}
