
package ci.itech.oedatarepo.service_impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.itech.oedatarepo.model.District;
import ci.itech.oedatarepo.repository.DictrictRepository;
import ci.itech.oedatarepo.service.DistrictService;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DictrictRepository repository;

	@Override
	public District create(District d) throws Exception {
		return repository.save(d);
	}

	@Override
	public District update(District d) throws Exception {
		return repository.saveAndFlush(d);
	}

	@Override
	public District createOrUpdate(District d) throws Exception {
		if (ObjectUtils.isEmpty(d.getId())) {
			return this.create(d);
		}
		return repository.saveAndFlush(d);
	}

	@Override
	public District getOne(int id) {
		District t;

		try {
			t = repository.findById(id).orElse(null);

		} catch (Exception ex) {
			return null;
		}
		return t;
	}

	@Override
	public List<District> getAll() {
		List<District> lst;

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

}
