package ci.itech.oedatarepo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ci.itech.oedatarepo.model.District;
import ci.itech.oedatarepo.model.Site;
import ci.itech.oedatarepo.service.DistrictService;
import ci.itech.oedatarepo.service.SiteService;

@Controller
@RequestMapping("/site")
public class SiteController {

	@Autowired
	private SiteService entityService;
	@Autowired
	private DistrictService districtService;

	@PostMapping(value = "")
	public String createSite(@Valid Site model) {

		entityService.create(model);
		return "redirect:/site";
	}

	@GetMapping(value = "")
	public String getAllSite(Model model) {
		List<District> districts = districtService.getAll();
		List<Site> sites = entityService.getAll();
		model.addAttribute("sites", sites);
		model.addAttribute("districts", districts);
		model.addAttribute("site", new Site());
		model.addAttribute("mode", 0); // add a new entry
		return "organizations/site";
	}

	@GetMapping(value = "/{id}")
	public String getOneSite(@PathVariable("id") int id, Model model) {
		List<Site> sites = entityService.getAll();
		List<District> districts = districtService.getAll();
		Site s = entityService.getOne(id);
		if (s == null) {
			s = new Site();
		}
		model.addAttribute("districts", districts);
		model.addAttribute("sites", sites);
		model.addAttribute("mode", 1); // modify an entry
		model.addAttribute("site", s);
		return "organizations/site";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteSite(@PathVariable("id") int id) {
		entityService.delete(id);
		return "redirect:/site";
	}

}
