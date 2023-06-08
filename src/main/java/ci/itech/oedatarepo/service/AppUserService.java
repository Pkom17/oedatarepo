package ci.itech.oedatarepo.service;

import java.util.List;
import java.util.Optional;

import ci.itech.oedatarepo.model.AppUser;

public interface AppUserService {
	AppUser create(AppUser d, boolean isPasswordCrypted);

	AppUser update(AppUser d, boolean isPasswordCrypted);

	AppUser getOne(Integer id);

	List<AppUser> getAll();

	public void updateLastLogin(String userName);

	public AppUser findUserByEmail(String email);

	public Optional<AppUser> findUserById(int id);

	public List<AppUser> findActiveUser();

	public List<AppUser> findUsersIdName();

	public AppUser findUserByPhoneContact(String contact);

	public List<AppUser> findUsers();

	long getTotal();

	boolean delete(Integer id);

	public void addSiteToUser(String login, int siteId);

	public void removeSite(String login, int siteId);

	public void removeAllSites(String login);

	public void addRoleToUser(String login, String role);

	public void removeRole(String login, String role);

	public void removeAllRoles(String login);

}
