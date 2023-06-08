package ci.itech.oedatarepo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ci.itech.oedatarepo.model.Site;


public interface SiteRepository  extends JpaRepository<Site, Integer> , JpaSpecificationExecutor<Site> {

}
