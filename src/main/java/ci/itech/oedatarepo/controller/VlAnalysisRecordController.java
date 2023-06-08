package ci.itech.oedatarepo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ci.itech.oedatarepo.model.Platform;
import ci.itech.oedatarepo.model.Site;
import ci.itech.oedatarepo.model.VlAnalysisRecord;
import ci.itech.oedatarepo.service.FacilityService;
import ci.itech.oedatarepo.service.PlatformService;
import ci.itech.oedatarepo.service.SiteService;
import ci.itech.oedatarepo.service.VlAnalysisRecordService;

@RestController
@RequestMapping("/vl_record")
public class VlAnalysisRecordController {

	@Autowired
	private VlAnalysisRecordService vlAnalysisRecordService;
	@Autowired
	private PlatformService platformService;
	@Autowired
	private SiteService siteService;
	private List<VlAnalysisRecord> record = new ArrayList<>();
	private Map<String, Integer> mapDatimCode;

	@PostMapping(value = "")
	public ResponseEntity<String> create(@RequestBody List<VlAnalysisRecord> analysis) {
		try {
			if (analysis.size() > 0) {
				processInsertRecord(analysis);
				vlAnalysisRecordService.createOrUpdateAll(record);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	private void processInsertRecord(List<VlAnalysisRecord> analysis) {
		mapDatimCode = siteService.getSitesDatimCodeMap();
		updateSiteList(analysis);
		//remove Ids
		record = analysis.stream().map(a -> {
			a.setId(null);
			return a;
		}).collect(Collectors.toList());
		
		List<String> allLabnos = analysis.stream().map(a -> a.getLabno()).collect(Collectors.toList());
		List<VlAnalysisRecord> existingRecords = vlAnalysisRecordService.findByLabnos(allLabnos);
		// create hashMap for labno -> analysis
		Map<String,VlAnalysisRecord> mapRecord = new HashMap<>();
		for (VlAnalysisRecord r : existingRecords) {
			mapRecord.put(r.getLabno(), r);
		}

		String platformName = analysis.get(0).getPlatform();
		Platform platform = platformService.getLabByName(platformName);
		if(ObjectUtils.isEmpty(platform)){
			platform = new Platform();
			platform.setName(platformName);
			platform.setLastUpdatedAt(new Date());
			platform.setLastPushedAt(new Date());
			platform =  platformService.create(platform);
		}
		platform.setLastPushedAt(new Date());
		platformService.update(platform);

		for (VlAnalysisRecord r : record) {
			if(ObjectUtils.isNotEmpty(mapRecord.getOrDefault(r.getLabno(), null))){
				//update
				r.setId(mapRecord.get(r.getLabno()).getId());
			}
			r.setPlatformId(platform.getId());
			if(ObjectUtils.isNotEmpty(r.getSiteDatimCode())){
				r.setSiteId(mapDatimCode.getOrDefault(r.getSiteDatimCode(),null));
			}
			r.setLastupdateddAt(new Date());
		}
	}

	@Autowired
	FacilityService fservice;

	private void updateSiteList(List<VlAnalysisRecord> analysis){
		//create site if nots exists
		for (VlAnalysisRecord r : analysis) {
			if(ObjectUtils.isEmpty(r.getSiteDatimCode())){
				continue;
			}
			if(mapDatimCode.containsKey(r.getSiteDatimCode())){
				continue;
			}
			Site site = new Site();
			site.setDatimCode(r.getSiteDatimCode());
			site.setDatimName(r.getSiteDatimName());
			site.setName(r.getSiteName());
			site.setDiisCode(r.getSiteCode());
			site.setLastUpdatedAt(new Date());
			Site s = siteService.create(site);
			mapDatimCode.put(s.getDatimCode(), s.getId());
		}
	}
}
