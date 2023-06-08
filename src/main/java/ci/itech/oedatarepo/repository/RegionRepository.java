
package ci.itech.oedatarepo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ci.itech.oedatarepo.model.Region;

public interface RegionRepository  extends JpaRepository<Region, Integer> , JpaSpecificationExecutor<Region> {

}
