package br.edu.ufersa.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ufersa.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService{

	private Logger logger = Logger.getLogger(UserServices.class.getName());

	@Autowired
	UserRepository repo;

	public UserServices(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username);
		var user = repo.findByUsername(username);
		if(user != null) {
			return user;
		}else {
			throw new UsernameNotFoundException("Username " + username + " not found.");
		}
	}
}
