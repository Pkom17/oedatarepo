package ci.itech.oedatarepo.service;

import java.util.List;

import ci.itech.oedatarepo.model.Region;

public interface RegionService {
	Region create(Region d) throws Exception;

	Region update(Region d) throws Exception;

	Region createOrUpdate(Region d) throws Exception;

	Region getOne(int id);

	List<Region> getAll();

	long getTotal();

	boolean delete(int id);
}
