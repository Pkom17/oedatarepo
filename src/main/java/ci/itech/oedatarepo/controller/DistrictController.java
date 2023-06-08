
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
import ci.itech.oedatarepo.model.Region;
import ci.itech.oedatarepo.service.DistrictService;
import ci.itech.oedatarepo.service.RegionService;

@Controller
@RequestMapping("/district")
public class DistrictController {

	@Autowired
	private DistrictService entityService;
	@Autowired
	private RegionService regionService;

	@PostMapping(value = "")
	public String createDistrict(@Valid District district) {

		try {
			entityService.createOrUpdate(district);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/district";
	}

	@GetMapping(value = "")
	public String getAllDistrict(Model model) {
		List<District> districts = entityService.getAll();
		List<Region> regions = regionService.getAll();
		model.addAttribute("regions", regions);
		model.addAttribute("districts", districts);
		model.addAttribute("district", new District());
		model.addAttribute("mode", 0); //add a new entry
		return "organizations/district";
	}

	@GetMapping(value = "/{id}")
	public String getOneDistrict(@PathVariable("id") int id, Model model) {
		List<District> districts = entityService.getAll();
		List<Region> regions = regionService.getAll();
		District e = entityService.getOne(id);
		if (e == null) {
			e = new District();
		}
		model.addAttribute("districts", districts);
		model.addAttribute("regions", regions);
		model.addAttribute("mode", 1); //modify an entry
		model.addAttribute("district", e);
		return "organizations/district";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteDistrict(@PathVariable("id") int id) {
		entityService.delete(id);
		return "redirect:/district";
	}

}
