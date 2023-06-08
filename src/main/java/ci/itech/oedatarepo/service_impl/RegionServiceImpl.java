
package ci.itech.oedatarepo.service_impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.itech.oedatarepo.model.Region;
import ci.itech.oedatarepo.repository.RegionRepository;
import ci.itech.oedatarepo.service.RegionService;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository repository;

	@Override
	public Region create(Region d) throws Exception {

		return repository.save(d);
	}

	@Override
	public Region createOrUpdate(Region d) throws Exception {
		if (ObjectUtils.isEmpty(d.getId())) {
			return this.create(d);
		}
		return repository.saveAndFlush(d);
	}

	@Override
	public Region update(Region d) throws Exception {
		return repository.saveAndFlush(d);
	}

	@Override
	public Region getOne(int id) {
		Region t;

		try {
			t = repository.findById(id).orElse(null);

		} catch (Exception ex) {
			return null;
		}
		return t;
	}

	@Override
	public List<Region> getAll() {
		List<Region> lst;

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
