package ci.itech.oedatarepo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ci.itech.oedatarepo.model.District;


public interface DictrictRepository  extends JpaRepository<District, Integer> , JpaSpecificationExecutor<District> {

}
