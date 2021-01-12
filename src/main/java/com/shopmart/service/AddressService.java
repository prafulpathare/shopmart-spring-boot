package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Address;
import com.shopmart.model.Customer;
import com.shopmart.model.Order;
import com.shopmart.model.OrderItem;
import com.shopmart.model.User;
import com.shopmart.model.Address.AddressType;
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.CustomerRepository;
import com.shopmart.repository.OrderItemRepository;
import com.shopmart.repository.OrderRepository;

@Service
public class AddressService {
	@Autowired private UserService userService;
	@Autowired private AddressRepository addressRepository;
	

	public void create(Address address) {
		User user = userService.get();
		if(address.getAddress_type() == null) address.setAddress_type(AddressType.UNKNOWN);
		if(address.getAddress_type() == AddressType.HOME) {
			for (Address addressI: user.getAddresses()) {
				if(addressI.getAddress_type() == AddressType.HOME) addressI.setAddress_type(AddressType.UNKNOWN);
			}
		}
		if(address.getAddress_type() == AddressType.OFFICE) {
			for (Address addressI: user.getAddresses()) {
				if(addressI.getAddress_type() == AddressType.OFFICE) addressI.setAddress_type(AddressType.UNKNOWN);
			}
		}
		address.setUser(user);
		addressRepository.save(address);
	}
	public void delete(long addressId) {
		addressRepository.deleteById(addressId);
	}
	
}
