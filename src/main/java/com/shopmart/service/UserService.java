package com.shopmart.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopmart.model.Address;
import com.shopmart.model.Address.AddressType;
import com.shopmart.model.User;
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired private JdbcTemplate jdbc;
	@Autowired private UserRepository userRepository;
	@Autowired private AddressRepository addressRepository;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("[loadUserByUsername] " + username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		}
		log.info("Logged -> "+user.toString());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	public long getUserId() {
		return userRepository.getUserIdFromUsername(this.getUsername());
	}
	public User get() {
		return userRepository.findByUsername(this.getUsername());
	}
	public void setEmail(String email) {
		User user = userRepository.findByUsername(this.getUsername());
		user.setEmail(email);
		userRepository.save(user);
	}
}
