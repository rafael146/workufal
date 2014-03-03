package dados.ddb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	
	private Connection connection;
	private String user;
	private String senha;
	private String driver;
	protected String dbName;
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
		this.url = url;
	}
	
	protected Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url+dbName, user, senha);
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

	
	public boolean executar(String sql) {
		try(Connection con = getConnection();
				PreparedStatement stm = con.prepareStatement(sql)) {
			return stm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void criarBanco() throws SQLException{
		try(Connection connection = DriverManager.getConnection(url, user, senha)) {
			connection.createStatement().execute("create database IF NOT EXISTS "+dbName+" DEFAULT CHARACTER SET utf8 ");
		}
		tableBolsista();
		tableUsuario();
		tableRotina();
		tableDoacao();

	}
	public void tableBolsista() throws SQLException{
		executar("create table if not exists bolsista("
				+ " id_bolsista double auto_increment,"
				+ " nome  varchar(30) not null,"
				+ " email varchar(35) not null, "
				+ "PRIMARY KEY (id_bolsista)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;");
	}

	public void tableDoacao() throws SQLException{
		executar("create table if not exists doacao("
				+ " nome_bolsista  varchar(30) not null,"
				+ " descricao varchar(35) not null, "
				+ " id_rotina double not null,"
				//+ "FOREIGN KEY (nome_bolsista) REFERENCES doacaoLamp.bolsista (nome) ,"
				//+ "FOREIGN KEY (rotina) REFERENCES doacaoLamp.rotina (rotina) ,"
				+ "primary key (nome_bolsista,id_rotina)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;");
	}
	public void tableUsuario() throws SQLException{
		executar("create table if not exists usuario("
				+ " login varchar(30) not null,"
				+ " senha varchar(35) not null , "
				+ " primary key (login)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;");
	}
	
	private void tableRotina() throws SQLException {
		executar("create table if not exists rotina("
				+ " id_rotina double auto_increment,"
				+ "primary key (id_rotina)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;");
	}

}
