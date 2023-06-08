package ci.itech.oedatarepo.service_impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.itech.oedatarepo.model.VlAnalysisRecord;
import ci.itech.oedatarepo.repository.VlAnalysisRecordRepo;
import ci.itech.oedatarepo.service.VlAnalysisRecordService;

@Service
@Transactional
public class VlAnalysisRecordServiceImpl implements VlAnalysisRecordService {
    @Autowired
    private VlAnalysisRecordRepo vlAnalysisRecordRepo;

    Logger logger = LoggerFactory.getLogger(VlAnalysisRecordServiceImpl.class);

    @Override
    public VlAnalysisRecord create(VlAnalysisRecord record) {
        if (ObjectUtils.isNotEmpty(record)) {
            return vlAnalysisRecordRepo.save(record);
        }
        return null;
    }

    @Override
    public VlAnalysisRecord update(VlAnalysisRecord record) {
        try {
            if (ObjectUtils.isNotEmpty(record)) {
                if (ObjectUtils.isNotEmpty(record.getId())) {
                    return vlAnalysisRecordRepo.saveAndFlush(record);
                } else {
                    create(record);
                }
            }
        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public List<VlAnalysisRecord> createOrUpdateAll(List<VlAnalysisRecord> recordList) {
        try {
            if (ObjectUtils.isNotEmpty(recordList)) {
                return vlAnalysisRecordRepo.saveAll(recordList);
            }
        } catch (Exception e) {
            logger.error("VlAnalysisRecordServiceImpl - createOrUpdateAll(...)", e);
        }
        return null;
    }

    @Override
    public VlAnalysisRecord getOne(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public List<VlAnalysisRecord> getAll() {
        return vlAnalysisRecordRepo.findAll();
    }

    @Override
    public int getTotal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotal'");
    }

    @Override
    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public VlAnalysisRecord findByLabno(String labno) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByLabno'");
    }

    @Override
    public List<VlAnalysisRecord> findByLabnos(List<String> labnos) {
        return vlAnalysisRecordRepo.findAllByLabnoIn(labnos);
    }

    @Override
    public List<VlAnalysisRecord> findByRegionId(Integer regionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByRegionId'");
    }

    @Override
    public List<VlAnalysisRecord> findByDistrictId(Integer districtId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDistrictId'");
    }

    @Override
    public List<VlAnalysisRecord> findBySiteId(Integer sitetId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBySiteId'");
    }

    @Override
    public List<VlAnalysisRecord> findByPatientCode(String patientCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPatientCode'");
    }

    @Override
    public List<VlAnalysisRecord> findByAnalysisStatus(String status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VlAnalysisRecord> findByPlatform(String platform) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPlatform'");
    }

    @Override
    public List<VlAnalysisRecord> findByPlatformId(Integer platformId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByPlatformId'");
    }

    @Override
    public List<VlAnalysisRecord> findBySiteName(String siteName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBySiteName'");
    }

    @Override
    public List<VlAnalysisRecord> findByDate(String start, String end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDate'");
    }

    @Override
    public Page<VlAnalysisRecord> getAll(Pageable pageable) {
        return vlAnalysisRecordRepo.findAll(pageable);
    }

    @Override
    public Page<VlAnalysisRecord> getAll(String patientCode, Integer siteId, Integer platformId, String status,
            Date startDate, Date endDate, Pageable pageable) {
        if (ObjectUtils.isNotEmpty(patientCode)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPatientCodeAndStatusAndBetweenCollectionDate(patientCode,
                        status, startDate, endDate, pageable);
            }
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                return vlAnalysisRecordRepo.findAllByPatientCodeAndBetweenCollectionDate(patientCode,
                        startDate, endDate, pageable);
            }
            if (ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPatientCodeAndStatus(patientCode,
                        status, pageable);
            }
            return vlAnalysisRecordRepo.findAllByPatientCode(patientCode, pageable);
        }
        if (ObjectUtils.isNotEmpty(siteId)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllBySiteIdAndStatusAndBetweenCollectionDate(siteId,
                        status, startDate, endDate, pageable);
            }
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                return vlAnalysisRecordRepo.findAllBySiteIdAndBetweenCollectionDate(siteId,
                        startDate, endDate, pageable);
            }
            if (ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllBySiteIdAndStatus(siteId,
                        status, pageable);
            }
            return vlAnalysisRecordRepo.findAllBySiteId(siteId, pageable);
        }
        if (ObjectUtils.isNotEmpty(platformId)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPlatformIdAndStatusAndBetweenCollectionDate(platformId,
                        status, startDate, endDate, pageable);
            }
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                return vlAnalysisRecordRepo.findAllByPlatformIdAndBetweenCollectionDate(platformId,
                        startDate, endDate, pageable);
            }
            if (ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPlatformIdAndStatus(platformId,
                        status, pageable);
            }
            return vlAnalysisRecordRepo.findAllByPlatformId(platformId, pageable);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByStatusAndBetweenCollectionDate(
                        status, startDate, endDate, pageable);
            }
            return vlAnalysisRecordRepo.findAllByStatus(status, pageable);
        }
        return vlAnalysisRecordRepo.findAll(pageable);
    }

}
