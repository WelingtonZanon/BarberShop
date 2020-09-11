package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DB {

	final private static String drive = "org.apache.derby.jdbc.ClientDriver";
	final private static String url = "jdbc:derby:DerbyDB";
	final private static String usuario = "minos";
	final private static String senha = "minos";

	private static Connection conn = null;

	public static Connection getConnection() {
		boolean result = true;
		if (conn == null) {
			try {
				Class.forName(drive);
				conn = DriverManager.getConnection(url, usuario, senha);
				//JOptionPane.showMessageDialog(null,"conectou");
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} catch (ClassNotFoundException Drive) {
				JOptionPane.showMessageDialog(null, "Drive não localizado:" + Drive);
				result = false;
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
