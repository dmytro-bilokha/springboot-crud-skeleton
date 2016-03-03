package bilokhado.phonebook.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bilokhado.phonebook.dao.UserDao;
import bilokhado.phonebook.entity.User;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDao userDao;

	@ModelAttribute("user")
	public User addUser() {
		return new User();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getRegisterForm(Model model) {
		return "/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors())
			return "/register";
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.persistUser(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:/hello";
	}

}
