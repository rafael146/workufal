package dados;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	protected Connection conector ;
	protected Statement stm;

	protected  InfoBD info = new InfoBD();
	protected String sql;

	public boolean conectar(){

		try {
			Class.forName(info.driver);
			conector = DriverManager.getConnection(info.URL, info.USER, info.SENHA);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void desconectar() throws SQLException{
		conector.close();
	}
	public boolean executar(String sql) throws SQLException{
		try{
			conectar();
			stm = null;
			stm = conector.createStatement();
			stm.executeUpdate(sql);
			desconectar();
			return true;
		}catch(Exception e){
			e.printStackTrace();}
		return false;

	}
	public void criarBanco() throws SQLException{
		sql = "create database IF NOT EXISTS doacaoLamp  DEFAULT CHARACTER SET utf8 ";
		executar(sql);
		tableBolsista();
		tableUsuario();
		tableRotina();
		tableDoacao();

	}
	public void tableBolsista() throws SQLException{
		sql = "create table if not exists doacaoLamp.bolsista("
				+ " id_bolsista double auto_increment,"
				+ " nome  varchar(30) not null,"
				+ " email varchar(35) not null, "
				+ "PRIMARY KEY (id_bolsista)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;"; 
		executar(sql);
	}

	public void tableDoacao() throws SQLException{
		sql = "create table if not exists doacaoLamp.doacao("
				+ " nome_bolsista  varchar(30) not null,"
				+ " descricao varchar(35) not null, "
				+ " id_rotina double not null,"
				//+ "FOREIGN KEY (nome_bolsista) REFERENCES doacaoLamp.bolsista (nome) ,"
				//+ "FOREIGN KEY (rotina) REFERENCES doacaoLamp.rotina (rotina) ,"
				+ "primary key (nome_bolsista,id_rotina)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;" ;
		executar(sql);
	}
	public void tableUsuario() throws SQLException{
		sql = "create table if not exists doacaoLamp.usuario("
				+ " login varchar(30) not null,"
				+ " senha varchar(35) not null , "
				+ " primary key (login)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;"; 
		executar(sql);
	}
	private void tableRotina() throws SQLException {
		sql = "create table if not exists doacaoLamp.rotina("
				+ " id_rotina double auto_increment,"
				+ "primary key (id_rotina)) "
				+ "ENGINE = InnoDB "
				+ "DEFAULT CHARACTER SET = utf8;" ;
		executar(sql);
		
	}
}





