package ci.itech.oedatarepo.service;

import java.util.List;

import ci.itech.oedatarepo.model.District;

public interface DistrictService {
	District create(District d) throws Exception;

	District update(District d) throws Exception;

	District createOrUpdate(District d) throws Exception;

	District getOne(int id);

	List<District> getAll();

	long getTotal();

	boolean delete(int id);
}
