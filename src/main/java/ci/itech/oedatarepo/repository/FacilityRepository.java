package ci.itech.oedatarepo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ci.itech.oedatarepo.model.Facility;


public interface FacilityRepository  extends JpaRepository<Facility, Integer> , JpaSpecificationExecutor<Facility> {
    public Facility findByDatimCode(String datimCode);
}
