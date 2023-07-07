package ci.itech.oedatarepo.service_impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    @PersistenceContext
    private EntityManager em;

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

    @Override
    public List<VlAnalysisRecord> getAll(String patientCode, Integer siteId, Integer platformId, String status,
            Date startDate, Date endDate) {
        if (ObjectUtils.isNotEmpty(patientCode)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPatientCodeAndStatusAndBetweenCollectionDate(patientCode,
                        status, startDate, endDate);
            }
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                return vlAnalysisRecordRepo.findAllByPatientCodeAndBetweenCollectionDate(patientCode,
                        startDate, endDate);
            }
            if (ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPatientCodeAndStatus(patientCode,
                        status);
            }
            return vlAnalysisRecordRepo.findAllByPatientCode(patientCode);
        }
        if (ObjectUtils.isNotEmpty(siteId)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllBySiteIdAndStatusAndBetweenCollectionDate(siteId,
                        status, startDate, endDate);
            }
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                return vlAnalysisRecordRepo.findAllBySiteIdAndBetweenCollectionDate(siteId,
                        startDate, endDate);
            }
            if (ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllBySiteIdAndStatus(siteId,
                        status);
            }
            return vlAnalysisRecordRepo.findAllBySiteId(siteId);
        }
        if (ObjectUtils.isNotEmpty(platformId)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPlatformIdAndStatusAndBetweenCollectionDate(platformId,
                        status, startDate, endDate);
            }
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                return vlAnalysisRecordRepo.findAllByPlatformIdAndBetweenCollectionDate(platformId,
                        startDate, endDate);
            }
            if (ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByPlatformIdAndStatus(platformId,
                        status);
            }
            return vlAnalysisRecordRepo.findAllByPlatformId(platformId);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)
                    && ObjectUtils.isNotEmpty(status)) {
                return vlAnalysisRecordRepo.findAllByStatusAndBetweenCollectionDate(
                        status, startDate, endDate);
            }
            return vlAnalysisRecordRepo.findAllByStatus(status);
        }
        return vlAnalysisRecordRepo.findAll();
    }

    @Override
    public List<Map<String, Object>> getAllGroupBySite(Integer platformId, Date startDate, Date endDate) {

        return new ArrayList<>();
        // if (ObjectUtils.isNotEmpty(platformId)) {
        // if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
        // return findSiteGroupByPlatformIdAndBetweenCollectionDate(platformId,
        // startDate,
        // endDate);
        // } else {
        // return findSiteGroupByPlatformId(platformId);
        // }
        // } else {
        // if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
        // return findSiteGroupBetweenCollectionDate(startDate, endDate);
        // }
        // }
        // return findSiteGroup();
    }

    @Override
    public List<Map<String, Object>> getAllGroupByPlatform(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "select platform, count(*) total,count(status='1' or null) saisie,count(status='2' or null) technique,"
                + " count(status='3' or null) biologique , count(status='4' or null) echec,count(status='5' or null) non_conforme,count(status='6' or null) rejete"
                + " from vl_data_record vdr where 1=1 ";

        if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
            sql += " and collection_date between :start and :end ";
        }

        sql += "group by platform ";
        List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        try {
            Query query = em.createNativeQuery(sql);
            if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
                query.setParameter("start", sdf.format(startDate));
                query.setParameter("end", sdf.format(endDate));
            }
            List<Object[]> results = query.getResultList();
            for (Object[] o : results) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("platform", o[0]);
                map.put("total", o[1]);
                map.put("entered", o[2]);
                map.put("processed", o[3]);
                map.put("validated", o[4]);
                map.put("failed", o[5]);
                map.put("nonconform", o[6]);
                map.put("rejected", o[7]);
                response.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
