package ci.itech.oedatarepo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ci.itech.oedatarepo.config.UserValidator;
import ci.itech.oedatarepo.service.AppUserService;
import ci.itech.oedatarepo.service.LoginService;
import ci.itech.oedatarepo.service.VlAnalysisRecordService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private AppUserService userService;

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private VlAnalysisRecordService analysisService;

	private static Logger logger = LoggerFactory.getLogger(AnalysisController.class);

	@GetMapping(value = "/dashboard")
	public String index() {
		return "redirect:/dashboard";
	}

	@GetMapping(value = "")
	public String analysisIndex(Model model,
			@RequestParam(required = false, name = "start", defaultValue = "") String startDateString,
			@RequestParam(required = false, name = "end", defaultValue = "") String endDateString) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;
		Date endDate = null;
		if (ObjectUtils.isNotEmpty(startDateString) && ObjectUtils.isNotEmpty(endDateString)) {
			try {
				if (ObjectUtils.isEmpty(startDateString) || ObjectUtils.isEmpty(endDateString)) {
					throw new Exception("Must fill both start and end date");
				}
				startDate = sdf.parse(startDateString);
				endDate = sdf.parse(endDateString);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				startDate = null;
				startDateString = null;
				endDate = null;
				endDateString = null;
			}
		}
		List<Map<String, Object>> analysis = analysisService.getAllGroupByPlatform(startDate, endDate);
		model.addAttribute("analysis", analysis);
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
