package bilokhado.phonebook.controller;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bilokhado.phonebook.dao.UserDao;
import bilokhado.phonebook.entity.User;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	UserDao userDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	public String getRegisterForm(Model model) {
		return "/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerUser(@RequestParam("login") String login, 
			@RequestParam("password") String password) {
		User user = userDao.createNewUser();
		user.setLogin(login);
		String passwordHash = passwordEncoder.encode(password);
		user.setPasswordHash(passwordHash);
		userDao.persistUser(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(login, passwordHash,
				Arrays.asList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:/hello";
	}

}
