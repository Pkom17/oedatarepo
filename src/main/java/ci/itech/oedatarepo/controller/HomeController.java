package ci.itech.oedatarepo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ci.itech.oedatarepo.config.UserValidator;
import ci.itech.oedatarepo.service.AppUserService;
import ci.itech.oedatarepo.service.LoginService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private AppUserService userService;

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping(value = "/dashboard")
	public String index() {
		return "redirect:/dashboard";
	}

	@GetMapping(value = "")
	public String analysisIndex() {
		return "home/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String error403() {
		return "error";
	}

}
