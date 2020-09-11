package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ServiceDao;
import model.entities.Service;

public class ServiceService {

	private ServiceDao dao = DaoFactory.createServiceDao();
	
	public List<Service> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Service obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Service obj) {
		dao.deleteById(obj.getId());
	}
}
