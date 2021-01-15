package com.shopmart.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopmart.model.User;
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired public JdbcTemplate jdbc;
	@Autowired public PasswordEncoder bcryPasswordEncoder;
	@Autowired public UserRepository userRepository;
	@Autowired private AddressRepository addressRepository;	
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("customer not found: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	public void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	public long getId() {
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
	
	public boolean emailExists(String email) {
		return jdbc.queryForObject("select count(email) from shopmart.users where email = ?", new Object[] {email}, Integer.class) == 0 ? false : true;
	}
}
