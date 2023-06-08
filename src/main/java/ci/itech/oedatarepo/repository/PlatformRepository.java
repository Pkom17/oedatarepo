
package ci.itech.oedatarepo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ci.itech.oedatarepo.model.Platform;

public interface PlatformRepository  extends JpaRepository<Platform, Integer> , JpaSpecificationExecutor<Platform> {
    public Platform findByName(String name);
}
