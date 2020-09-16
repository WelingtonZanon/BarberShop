package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.ProductDao;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao {

	private Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Product findByName(String name) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM DERBYDB.PRODUCT WHERE PRODUCT_NAME = ?");
			st.setString(2, name);
			rs = st.executeQuery();
			if (rs.next()) {
				Product obj = new Product();
				obj.setId(rs.getInt("PRODUCT_ID"));
				obj.setName(rs.getString("PRODUCT_NAME"));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM DERBYDB.PRODUCT ORDER BY PRODUCT_NAME");
			rs = st.executeQuery();

			List<Product> list = new ArrayList<>();

			while (rs.next()) {
				Product obj = new Product();
				obj.setId(rs.getInt("PRODUCT_ID"));
				obj.setName(rs.getString("PRODUCT_NAME"));
				obj.setQtd(rs.getInt("PRODUCT_ID"));
				obj.setValuein(rs.getDouble("PRODUCT_VALUEIN"));
				obj.setValueout(rs.getDouble("PRODUCT_VALUEOUT"));
				obj.setDescription(rs.getString("PRODUCT_DESCRIPTION"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO DERBYDB.PRODUCT " +
				"(PRODUCT_NAME,PRODUCT_QTD,PRODUCT_VALUEIN,PRODUCT_VALUEOUT,PRODUCT_DESCRIPTION) " +
				"VALUES " +
				"(?,?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setInt(2, obj.getQtd());
			st.setDouble(3, obj.getValuein());
			st.setDouble(4, obj.getValueout());
			st.setString(5, obj.getDescription());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE DERBYDB.PRODUCT " +
				"SET PRODUCT_NAME = ?, " +
				"PRODUCT_QTD = ?, " +
				"PRODUCT_VALUEIN = ?, " +
				"PRODUCT_VALUEOUT = ?, " +
				"PRODUCT_DESCRIPTION = ? " +
				"WHERE PRODUCT_ID = ?");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getQtd());
			st.setDouble(3, obj.getValuein());
			st.setDouble(4, obj.getValueout());
			st.setString(5, obj.getDescription());;
			st.setInt(6, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM DERBYDB.PRODUCT WHERE PRODUCT_ID = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}


}
