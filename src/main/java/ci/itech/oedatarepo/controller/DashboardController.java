
package ci.itech.oedatarepo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ci.itech.oedatarepo.service.DashboardService;
import ci.itech.oedatarepo.service.PlatformService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private PlatformService labService;

	@GetMapping("/")
	public String home() {
		return "Home";
	}
}
