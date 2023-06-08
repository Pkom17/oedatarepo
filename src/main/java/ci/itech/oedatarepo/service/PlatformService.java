package ci.itech.oedatarepo.service;

import java.util.List;
import java.util.Map;

import ci.itech.oedatarepo.model.Platform;

public interface PlatformService {
	Platform create(Platform d);

	Platform update(Platform d);

	Platform getOne(int id);

	Platform createOrUpdate(Platform d);
	
	List<Map<String, Object>> getLabsIdAndNames();

	List<Platform> getAll();

	Platform getLabByName(String name);

	long getTotal();

	boolean delete(int id);
}
