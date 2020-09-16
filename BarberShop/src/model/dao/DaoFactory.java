package model.dao;

import db.DB;
import model.dao.impl.ProductDaoJDBC;
import model.dao.impl.ServiceDaoJDBC;

public class DaoFactory {
	
	public static ServiceDao createServiceDao() {
		return new ServiceDaoJDBC(DB.getConnection());
	}
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
}
