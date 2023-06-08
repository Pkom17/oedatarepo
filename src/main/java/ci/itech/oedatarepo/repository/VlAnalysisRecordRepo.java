package ci.itech.oedatarepo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ci.itech.oedatarepo.model.VlAnalysisRecord;

public interface VlAnalysisRecordRepo
        extends JpaRepository<VlAnalysisRecord, Integer>, JpaSpecificationExecutor<VlAnalysisRecord> {

    public List<VlAnalysisRecord> findAllByLabnoIn(List<String> labnos);

    public Page<VlAnalysisRecord> findAllByPatientCode(String patientCode, Pageable pageable);

    public Page<VlAnalysisRecord> findAllByPatientCodeAndStatus(String patientCode,String status, Pageable pageable);

    public Page<VlAnalysisRecord> findAllBySiteId(Integer siteId, Pageable pageable);

    public Page<VlAnalysisRecord> findAllBySiteIdAndStatus(Integer siteId,String status,  Pageable pageable);

    public Page<VlAnalysisRecord> findAllByPlatformId(Integer platformId, Pageable pageable);

    public Page<VlAnalysisRecord> findAllByPlatformIdAndStatus(Integer platformId,String status,  Pageable pageable);

    public Page<VlAnalysisRecord> findAllByStatus(String status, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.patientCode = :patientCode AND v.entryDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPatientCodeAndBetweenEntryDate(String patientCode, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.siteId = :siteId AND v.entryDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBySiteIdAndBetweenEntryDate(Integer siteId,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.platformId = :platformId AND v.entryDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPlatformIdAndBetweenEntryDate(Integer platformId,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.status = :status AND v.entryDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByStatusAndBetweenEntryDate(String status,Date startDate, Date endDate, Pageable pageable);


    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.patientCode = :patientCode AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPatientCodeAndBetweenCollectionDate(String patientCode, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.patientCode = :patientCode AND status=:status AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPatientCodeAndStatusAndBetweenCollectionDate(String patientCode, String status,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.siteId = :siteId AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBySiteIdAndBetweenCollectionDate(Integer siteId,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.siteId = :siteId AND status=:status AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBySiteIdAndStatusAndBetweenCollectionDate(Integer siteId,String status,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.platformId = :platformId AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPlatformIdAndBetweenCollectionDate(Integer platformId,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.platformId = :platformId AND status=:status AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPlatformIdAndStatusAndBetweenCollectionDate(Integer platformId,String status,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.status = :status AND v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByStatusAndBetweenCollectionDate(String status,Date startDate, Date endDate, Pageable pageable);


    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.patientCode = :patientCode AND v.releasedDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPatientCodeAndBetweenReleasedDate(String patientCode, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.siteId = :siteId AND v.releasedDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBySiteIdAndBetweenReleasedDate(Integer siteId,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.platformId = :platformId AND v.releasedDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByPlatformIdAndBetweenReleasedDate(Integer platformId,Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.status = :status AND v.releasedDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllByStatusAndBetweenReleasedDate(String status,Date startDate, Date endDate, Pageable pageable);


    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.entryDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBetweenEntryDate(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.collectionDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBetweenCollectionDate(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT v FROM VlAnalysisRecord v WHERE v.releasedDate BETWEEN :startDate AND :endDate ")
    public Page<VlAnalysisRecord> findAllBetweenReleasedDate(Date startDate, Date endDate, Pageable pageable);


}