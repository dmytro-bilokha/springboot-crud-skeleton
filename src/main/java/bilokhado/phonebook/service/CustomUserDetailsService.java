package bilokhado.phonebook.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bilokhado.phonebook.dao.UserDao;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		bilokhado.phonebook.entity.User user = userDao.findByUserName(login);
		if (user == null) {
			logger.warn("User authentication failed for login: {}", login);
			throw new UsernameNotFoundException("User authentication failed for login: " + login);
		}
		return new User(user.getLogin(), user.getPasswordHash(), Arrays.asList(new SimpleGrantedAuthority("USER")));
	}

}
