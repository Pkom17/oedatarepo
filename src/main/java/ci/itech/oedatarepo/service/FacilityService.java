
package ci.itech.oedatarepo.service;

import java.util.List;

import ci.itech.oedatarepo.model.Facility;

public interface FacilityService {
	Facility getOneByDatimCode(String datimCode);

	Facility getOne(Integer id);

	List<Facility> getAll();

	long getTotal();

	boolean delete(int id);

}
