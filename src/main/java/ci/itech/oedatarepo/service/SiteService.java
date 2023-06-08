
package ci.itech.oedatarepo.service;

import java.util.List;
import java.util.Map;

import ci.itech.oedatarepo.model.Site;

public interface SiteService {
	Site create(Site d);

	Site update(Site d);

	Site createOrUpdate(Site d);

	Site getOne(int id);

	List<Site> getAll();
	
	List<Map<String, Object>> getSiteIdAndNames();

	List<Map<String, Object>> getSiteIdAndDatimCodes();

	Map<String, Integer> getSitesDatimCodeMap();

	long getTotal();

	boolean delete(int id);

	List<Integer> getSitesIdsByUser(Integer userId);
}
