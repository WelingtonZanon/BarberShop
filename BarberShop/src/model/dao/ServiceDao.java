package model.dao;

import java.util.List;

import model.entities.Service;

public interface ServiceDao {

	void insert(Service obj);
	void update(Service obj);
	void deleteById(Integer id);
	Service findByName(String name);
	List<Service> findAll();
}
