package ci.itech.oedatarepo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ci.itech.oedatarepo.config.CustomAccessDeniedHandler;
import ci.itech.oedatarepo.exception.OperationFailedException;
import ci.itech.oedatarepo.model.Platform;
import ci.itech.oedatarepo.model.Site;
import ci.itech.oedatarepo.model.VlAnalysisRecord;
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
	private static Logger logger = LoggerFactory.getLogger(VlAnalysisRecordController.class);

	@PreAuthorize("hasAnyRole('PUSHER')")
	@PostMapping(value = "")
	public ResponseEntity<String> create(@RequestBody List<VlAnalysisRecord> analysis) {
		try {
			if (!analysis.isEmpty()) {
				processInsertRecord(analysis);
				vlAnalysisRecordService.createOrUpdateAll(record);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	private void processInsertRecord(List<VlAnalysisRecord> analysis) {
		try {
			Map<String, Integer> prefixMap = platformService.getLabPrefixMap();
			mapDatimCode = siteService.getSitesDatimCodeMap();
			updateSiteList(analysis);
			// remove Ids
			record = analysis.stream().map(a -> {
				a.setId(null);
				return a;
			}).collect(Collectors.toList());

			List<String> allLabnos = analysis.stream().map(a -> a.getLabno()).collect(Collectors.toList());
			List<VlAnalysisRecord> existingRecords = vlAnalysisRecordService.findByLabnos(allLabnos);
			// create hashMap for labno -> analysis
			Map<String, VlAnalysisRecord> mapRecord = new HashMap<>();
			for (VlAnalysisRecord r : existingRecords) {
				mapRecord.put(r.getLabno(), r);
			}

			String prefixLabNo = analysis.get(0).getLabno().substring(0, 4);
			Integer platformId = prefixMap.get(prefixLabNo);

			Platform platform = platformService.getOne(platformId);
			if (ObjectUtils.isEmpty(platform)) {
				throw new OperationFailedException("unable to identify which is sending data");
			}
			platform.setLastPushedAt(new Date());
			platformService.update(platform);

			for (VlAnalysisRecord r : record) {
				if (ObjectUtils.isNotEmpty(mapRecord.getOrDefault(r.getLabno(), null))) {
					// update
					r.setId(mapRecord.get(r.getLabno()).getId());
				}
				r.setPlatformId(platform.getId());
				r.setPlatform(platform.getName());
				if (ObjectUtils.isNotEmpty(r.getSiteDatimCode())) {
					r.setSiteId(mapDatimCode.getOrDefault(r.getSiteDatimCode(), null));
				}
				r.setLastupdateddAt(new Date());
			}

		} catch (Exception e) {
			logger.error("processInsertRecord Error", e);
		}
	}

	private void updateSiteList(List<VlAnalysisRecord> analysis) {
		// create site if nots exists
		for (VlAnalysisRecord r : analysis) {
			if (ObjectUtils.isEmpty(r.getSiteDatimCode())) {
				continue;
			}
			if (mapDatimCode.containsKey(r.getSiteDatimCode())) {
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
