package ci.itech.oedatarepo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ci.itech.oedatarepo.model.VlAnalysisRecord;

public interface VlAnalysisRecordService {
    VlAnalysisRecord create(VlAnalysisRecord record);

	VlAnalysisRecord update(VlAnalysisRecord record);

    List<VlAnalysisRecord> createOrUpdateAll(List<VlAnalysisRecord> recordList);

	VlAnalysisRecord getOne(Integer id);

	List<VlAnalysisRecord> getAll();

	Page<VlAnalysisRecord> getAll(Pageable pageable);

    Page<VlAnalysisRecord> getAll(String patientCode,Integer siteId, Integer platformId, String status,  Date startDate, Date endDate,Pageable pageable);

	int getTotal();

	boolean delete(Integer id);

	VlAnalysisRecord findByLabno(String labno);

    List<VlAnalysisRecord> findByLabnos(List<String> labnos);

    List<VlAnalysisRecord> findByRegionId(Integer regionId);

    List<VlAnalysisRecord> findByDistrictId(Integer districtId);

    List<VlAnalysisRecord> findBySiteId(Integer sitetId);

    List<VlAnalysisRecord> findBySiteName(String siteName);

    List<VlAnalysisRecord> findByAnalysisStatus(String status);

    List<VlAnalysisRecord> findByDate(String start, String end);

    List<VlAnalysisRecord> findByPatientCode(String patientCode);

    List<VlAnalysisRecord> findByPlatform(String platform);

    List<VlAnalysisRecord> findByPlatformId(Integer platformId);
}
