package ci.itech.oedatarepo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ci.itech.oedatarepo.model.Platform;
import ci.itech.oedatarepo.service.PlatformService;

@Controller
@RequestMapping("/platform")
public class PlatformController {

	@Autowired
	private PlatformService entityService;

	@GetMapping(value = "")
	public String getAllLab(Model model) {
		List<Platform> platforms = entityService.getAll();
		model.addAttribute("platforms", platforms);
		return "platforms/index";
	}
}
