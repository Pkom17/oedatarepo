
package ci.itech.oedatarepo.service_impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.itech.oedatarepo.model.Site;
import ci.itech.oedatarepo.repository.SiteRepository;
import ci.itech.oedatarepo.service.SiteService;

@Service
@Transactional
public class SiteServiceImpl implements SiteService {

	@Autowired
	private SiteRepository repository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Site create(Site d) {

		Site entity;

		try {
			entity = repository.save(d);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return entity;
	}

	@Override
	public Site update(Site d) {
		Site c;

		try {
			c = repository.saveAndFlush(d);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return c;
	}

	@Override
	public Site createOrUpdate(Site d) {
		try {
			if (ObjectUtils.isEmpty(d.getId())) {
				return this.create(d);
			} else
				return repository.saveAndFlush(d);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Site getOne(int id) {
		Site t;

		try {
			t = repository.findById(id).orElse(null);

		} catch (Exception ex) {
			return null;
		}
		return t;
	}

	@Override
	public List<Map<String, Object>> getSiteIdAndNames() {
		String sql = "SELECT id,name FROM facilitys ORDER BY name ";
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		try {
			Query query = em.createNativeQuery(sql);
			List<Object[]> results = query.getResultList();
			for (Object[] o : results) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", o[0]);
				map.put("name", o[1]);
				response.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<Site> getAll() {
		List<Site> lst;

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
	public List<Map<String, Object>> getSiteIdAndDatimCodes() {
		String sql = "SELECT id,datim_code FROM facilitys ";
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		try {
			Query query = em.createNativeQuery(sql);
			List<Object[]> results = query.getResultList();
			for (Object[] o : results) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", o[0]);
				map.put("datim_code", o[1]);
				response.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Map<String, Integer> getSitesDatimCodeMap() {
		String sql = "SELECT id,datim_code FROM facilitys ";
		Map<String, Integer> response = new HashMap<String, Integer>();
		try {
			Query query = em.createNativeQuery(sql);
			List<Object[]> results = query.getResultList();
			for (Object[] o : results) {
				response.put(o[1].toString(), Integer.parseInt(o[0].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<Integer> getSitesIdsByUser(Integer userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getSitesIdsByUser'");
	}

}
