package com.bridgelabz.app.serviceimpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.app.model.LoginRequest;
import com.bridgelabz.app.model.User;
import com.bridgelabz.app.repository.UserRepository;
import com.bridgelabz.app.service.UserService;
import com.bridgelabz.app.util.EncryptPassword;
import com.bridgelabz.app.util.JsonToken;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepository userRep;



	String secretKey;
	String subject;

	@Override
	public String login(User user) {
		String password = encryptedPassword(user.getPassword());
		Optional<User> userList = userRep.findByEmailAndPassword(user.getEmail(), password);
		System.out.println("idddd :" + user.getId());
		System.out.println("User : " + userList.get());

		if (userList.get() != null) {
			System.out.println("Sucessful login");
			return userList.get().getName() + "-->" + JsonToken.jwtToken(password, userList.get().getId());
			
		} else
			System.out.println("wrong Id or password");
		return "wrong id or password";
	}

	@Override
	public User update(String token, User user) {
		 int verifiedUserId = JsonToken.tokenVerification(token);

	        Optional<User> maybeUser = userRep.findById(verifiedUserId);
	        User presentUser = maybeUser.map(existingUser -> {
	            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
	            existingUser.setPhonenumber(
	                    user.getPhonenumber() != null ? user.getPhonenumber() : maybeUser.get().getPhonenumber());
	            existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
	            existingUser
	                    .setPassword(user.getPassword() != null ? EncryptPassword.encryptedPassword(user.getPassword()) : maybeUser.get().getPassword());
	            return existingUser;
	        }).orElseThrow(() -> new RuntimeException("User Not Found"));

	        return userRep.save(presentUser);
	    }

	@Override
	public boolean delete(String token) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRep.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public User userRegistration(User user, HttpServletRequest request) {
		user.setPassword(encryptedPassword(user.getPassword()));

		userRep.save(user);
		//Optional<User> user1 = userRep.findById(user.getId());
		if (user != null) {
			System.out.println("Sucessfull reg");
			// Optional<User> maybeUser = userRep.findById(user.getId());

			String tokenGen = JsonToken.jwtToken("secretKey", user.getId());

		//	User u = user1.get();
			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordUrl = forgotPasswordUrl + "/activestatus/" + "token=" + tokenGen;
			System.out.println(forgotPasswordUrl);
			String subject = "User Activation";

			// String s= sendMail(u, forgotPasswordUrl,subject);
			// return "Mail Sent Successfully";
			return user;

		} else {
			System.out.println("Not sucessful reg");
		}
		return null;
	}

	@Override
	public String encryptedPassword(String password) {
//	        String passwordToHash = user.getPassword();
//	        System.out.println("password: " + passwordToHash);
	        String generatedPassword = null;
	        try {
	            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // Add password bytes to digest
	            md.update(password.getBytes());
	            // Get the hash's bytes
	            byte[] bytes = md.digest();
	            // This bytes[] has bytes in decimal format;
	            // Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < bytes.length; i++) {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            // Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        System.out.println("generated password :" + generatedPassword);

	        return generatedPassword;

	    }



	
	@Override
	public User getUserInfoByEmail(String email) {
		return userRep.findByEmail(email);

	}

	/*
	 * public String sendMail(User user,String urlPattern,String subject) {
	 * 
	 * MimeMessage message = sender.createMimeMessage(); MimeMessageHelper helper =
	 * new MimeMessageHelper(message);
	 * 
	 * 
	 * 
	 * try { helper.setTo(user.getEmail()); helper.setText(urlPattern);
	 * helper.setSubject(subject);
	 * 
	 * } catch (MessagingException e) { e.printStackTrace(); return
	 * "Error while sending mail .."; } sender.send(message); return
	 * "Mail Sent Success!";
	 * 
	 * 
	 * }
	 */
	public Optional<User> findById(int id) {
		return userRep.findById(id);
	}
	@Override
	public List<User> fetchData() {
		List<User> users = userRep.findAll();
		return users;

	}

	@Override
	public String login(LoginRequest loginReq) {
		// TODO Auto-generated method stub
		Optional<User> maybeUser = userRep.findByEmailAndPassword(loginReq.getEmail(),
				EncryptPassword.encryptedPassword(loginReq.getPassword()));
				System.out.println(maybeUser);

				if (maybeUser.isPresent()) {
				System.out.println("Sucessful login");
				return JsonToken.jwtToken(EncryptPassword.encryptedPassword(loginReq.getPassword()), maybeUser.get().getId());
				} 
				else
				return null;

	}
}