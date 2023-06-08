package ci.itech.oedatarepo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ci.itech.oedatarepo.model.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser, Integer>, JpaSpecificationExecutor<AppUser> {

	public AppUser findByEmail(String email);

	@Query(value = "select u from AppUser u where u.locked = 'N' and u.active = 'Y' ")
	public List<AppUser> findUsersIdName();

	public List<AppUser> findByRole(String role);

	public List<AppUser> findByActive(String active);

	public List<AppUser> findByLocked(String locked);

	public AppUser findByPhoneContact(String contact);

}
