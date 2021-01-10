package com.shopmart.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.MessagingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopmart.model.Address;
import com.shopmart.model.Password;
import com.shopmart.model.User;
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.repository.UserRepository;
import com.shopmart.util.AuthPassword;
import com.shopmart.util.Mail;

@Service
public class UserService implements UserDetailsService {

	@Autowired private UserRepository userRepo;
	@Autowired private AddressRepository addrRepo;
	@Autowired private JdbcTemplate jdbc;
	@Autowired private PasswordRepository pswdRepo;
	@Autowired private EmailService emailServ;
	@Autowired private PasswordEncoder bcryptEncoder;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepo.findByUserId(Long.parseLong(userId));
		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + userId);
		}
		log.info("Logged -> "+user.toString());
		return new org.springframework.security.core.userdetails.User(""+user.getUserId(),
				user.getPassword(), new ArrayList<>());
	}

	public Authentication auth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	public long getUserId() {
		return Long.parseLong(auth().getName());
	}
	public User save(User user) {

		String email = jdbc.queryForObject("select email from passwords where passwordid = "+user.getUserId()+" limit 1", String.class);

		User newUser = userRepo.save(new User(user.getName(),
				bcryptEncoder.encode(user.getPassword()),
				email, user.getContact(), 0, 0
		));
		log.info("Signed -> "+newUser.toString());
		newUser.setPassword(null);
		return newUser;
	}
	public User updateUser(User user) {
		User upusr = userRepo.findByUserId(user.getUserId());
		upusr.setContact(user.getContact());
		upusr.setEmail(user.getEmail());
		upusr.setName(user.getName());
		userRepo.save(upusr);
		log.info("Updated -> "+upusr.toString());
		return upusr;
	}
	public User getUser() {
		User user = userRepo.findByUserId(this.getUserId());
		user.setPassword(null);
		return user;
	}
	public List<Address> getAddresses(){
		return addrRepo.findByUserId(this.getUserId());
	}

	public boolean setPassword(AuthPassword pswd) {
		Password pswd2 = pswdRepo.findByPasswordId(pswd.passwordid);
		if(pswd2 == null) {
			return false;
		}
		else {
			User user = userRepo.findByEmail(pswd2.getEmail());
			if(user == null) {
				return false;
			}
			else {
				user.setPassword(bcryptEncoder.encode(pswd.password));
				userRepo.save(user);
				log.info("Password Set -> "+user.toString());
				return true;
			}
		}
	}
}
