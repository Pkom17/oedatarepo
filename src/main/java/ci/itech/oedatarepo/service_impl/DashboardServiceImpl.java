package ci.itech.oedatarepo.service_impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.itech.oedatarepo.service.DashboardService;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

	@PersistenceContext
	private EntityManager em;

	
}
