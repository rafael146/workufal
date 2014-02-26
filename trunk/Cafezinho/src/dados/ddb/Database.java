package dados.ddb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	private Connection connection;
	private String user;
	private String senha;
	private String driver;
	private String dbName;
	private String url;
	
	public Database(String user, String senha, String dbName) {
		this(user, senha, dbName, "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/");
	}
	
	public Database(String user, String senha, String dbName, String url) {
		this(user, senha, dbName, "com.mysql.jdbc.Driver", url);
	}
	
	public Database(String user, String senha, String dbName, String driver, String url) {
		this.user = user;
		this.senha = senha;
		this.dbName = dbName;
		this.driver = driver;
		this.url = url+dbName;
	}
	
	private Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, senha);
			return connection;
		} catch (Exception e) {
		}
		return null;
	}
	
	protected boolean reconectar() {
		try {
			connection = DriverManager.getConnection(url, user, senha);
			return !connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private boolean desconectar() {
		try {
			connection.close();
			return connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet query(String sql) {
		try(Connection con = getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {
			stm.execute();
			return stm.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean executar(String sql) {
		try(Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(sql)) {
			return stm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
