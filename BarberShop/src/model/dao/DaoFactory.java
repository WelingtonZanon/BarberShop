package model.dao;

import db.DB;
import model.dao.impl.ServiceDaoJDBC;

public class DaoFactory {
	
	public static ServiceDao createServiceDao() {
		return new ServiceDaoJDBC(DB.getConnection());
	}
}
