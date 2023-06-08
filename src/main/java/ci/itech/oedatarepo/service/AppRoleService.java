
package ci.itech.oedatarepo.service;

import java.util.List;

import ci.itech.oedatarepo.model.AppRole;

public interface AppRoleService {
	AppRole create(AppRole d);

	AppRole update(AppRole d);

	AppRole getOne(Integer id);

	List<AppRole> getAll();

	long getTotal();

	boolean delete(Integer id);
	
}
