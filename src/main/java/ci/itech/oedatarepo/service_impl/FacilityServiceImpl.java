
package ci.itech.oedatarepo.service_impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.itech.oedatarepo.model.Facility;
import ci.itech.oedatarepo.repository.FacilityRepository;
import ci.itech.oedatarepo.service.FacilityService;

@Service
@Transactional
public class FacilityServiceImpl implements FacilityService {

	@Autowired
	private FacilityRepository repository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Facility> getAll() {
		List<Facility> lst;

		try {
			lst = repository.findAll();

		} catch (Exception ex) {
			return Collections.emptyList();
		}
		return lst;
	}

	@Override
	public long getTotal() {
		long total;

		try {
			total = repository.count();
		} catch (Exception ex) {
			return 0;
		}
		return total;
	}

	@Override
	public boolean delete(int id) {
		try {
			repository.deleteById(id);
			return true;

		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public Facility getOneByDatimCode(String datimCode) {
		Facility t;
		try {
			t = repository.findByDatimCode(datimCode);
		} catch (Exception ex) {
			return null;
		}
		return t;
	}

	@Override
	public Facility getOne(Integer id) {
		Facility t;
		try {
			t = repository.findById(id).orElse(null);
		} catch (Exception ex) {
			return null;
		}
		return t;
	}

}
