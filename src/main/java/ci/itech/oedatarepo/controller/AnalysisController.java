
package ci.itech.oedatarepo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import ci.itech.oedatarepo.model.VlAnalysisRecord;
import ci.itech.oedatarepo.service.PlatformService;
import ci.itech.oedatarepo.service.SiteService;
import ci.itech.oedatarepo.service.VlAnalysisRecordService;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {

	@Autowired
	private VlAnalysisRecordService analysisService;
	@Autowired
	private PlatformService platformService;
	@Autowired
	private SiteService siteService;

	private static Logger logger = LoggerFactory.getLogger(AnalysisController.class);
	private Map<String, String> mapStatus;

	@GetMapping(value = "")
	public String getAnalysis(Model model, @RequestParam(defaultValue = "collectionDate,desc") String[] sort,
			@RequestParam(required = false, name = "patient_code", defaultValue = "") String patientCode,
			@RequestParam(required = false, name = "site", defaultValue = "0") Integer site,
			@RequestParam(required = false, name = "platform", defaultValue = "0") Integer platform,
			@RequestParam(required = false, name = "status", defaultValue = "") String status,
			@RequestParam(required = false, name = "start", defaultValue = "") String startDateString,
			@RequestParam(required = false, name = "end", defaultValue = "") String endDateString,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
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
		if (site == 0) {
			site = null;
		}
		if (platform == 0) {
			platform = null;
		}
		if (status.equals("0")) {
			status = null;
		}
		patientCode = HtmlUtils.htmlEscape(patientCode.trim());
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(this.getSortOrder(sort)));

		Page<VlAnalysisRecord> analysisRecords = analysisService.getAll(patientCode, site, platform, status,
				startDate, endDate, pageable);
		List<Map<String, Object>> sites = siteService.getSiteIdAndNames();
		List<Map<String, Object>> platforms = platformService.getLabsIdAndNames();
		fillStatus();
		model.addAttribute("sites", sites);
		model.addAttribute("platforms", platforms);
		model.addAttribute("analysis", analysisRecords.getContent());
		model.addAttribute("totalElements", analysisRecords.getTotalElements());
		model.addAttribute("totalPages", analysisRecords.getTotalPages());
		model.addAttribute("size", analysisRecords.getSize());
		model.addAttribute("currentPage", analysisRecords.getNumber() + 1);
		model.addAttribute("numberOfElements", analysisRecords.getNumberOfElements());
		model.addAttribute("first", analysisRecords.isFirst());
		model.addAttribute("last", analysisRecords.isLast());
		model.addAttribute("empty", analysisRecords.isEmpty());
		model.addAttribute("selectedPatientCode", patientCode);
		model.addAttribute("selectedSite", site);
		model.addAttribute("selectedPlatform", platform);
		model.addAttribute("selectedStatus", status);
		model.addAttribute("selectedStartDate", startDateString);
		model.addAttribute("selectedEndDate", endDateString);
		model.addAttribute("mapStatus", mapStatus);

		return "analysis/index";
	}

	// @GetMapping(value = "")
	// public String getAllDistrict(Model model) {
	// List<District> districts = entityService.getAll();
	// List<Region> regions = regionService.getAll();
	// model.addAttribute("regions", regions);
	// model.addAttribute("districts", districts);
	// model.addAttribute("district", new District());
	// model.addAttribute("mode", 0); //add a new entry
	// return "organizations/district";
	// }

	// @GetMapping(value = "/{id}")
	// public String getOneDistrict(@PathVariable("id") int id, Model model) {
	// List<District> districts = entityService.getAll();
	// List<Region> regions = regionService.getAll();
	// District e = entityService.getOne(id);
	// if (e == null) {
	// e = new District();
	// }
	// model.addAttribute("districts", districts);
	// model.addAttribute("regions", regions);
	// model.addAttribute("mode", 1); //modify an entry
	// model.addAttribute("district", e);
	// return "organizations/district";
	// }

	// @GetMapping(value = "/delete/{id}")
	// public String deleteDistrict(@PathVariable("id") int id) {
	// entityService.delete(id);
	// return "redirect:/district";
	// }

	protected List<Order> getSortOrder(String[] sorts) {
		List<Order> orders = new ArrayList<>();
		if (sorts[0].contains(",")) {
			for (String sortOrder : sorts) {
				String[] sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}
		} else {
			orders.add(new Order(getSortDirection(sorts[1]), sorts[0]));
		}

		return orders;
	}

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}
		return Sort.Direction.ASC;
	}

	private void fillStatus() {
		mapStatus = new HashMap<>();
		mapStatus.put("1", "Saisie");
		mapStatus.put("2", "Validation Technique");
		mapStatus.put("3", "Validation Biologique");
		mapStatus.put("4", "Echec");
		mapStatus.put("5", "Non-Conforme");
		mapStatus.put("6", "Rejété");
	}

}
