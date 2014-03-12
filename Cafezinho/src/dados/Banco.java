package dados;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Bolsista;
import model.Doacao;
import model.Rotina;
import model.Usuario;

public class Banco implements ConsultasBanco{
	//Padrao Singleton
	private static Banco INSTANCE;
	Database banco = new Database();
	Bolsista b = new Bolsista("", "");
	private ResultSet rs;

	@Override
	public void addUsuarioBanco(Usuario usuario){
		try {
			banco.executar("insert into doacaoLamp.usuario( login , senha) values('" + usuario.getLogin() +"' , '" 
					+ usuario.getSenha() + "' ); ");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addBolsistaBanco(Bolsista bolsa){
		try {
			banco.executar("insert into doacaoLamp.bolsista(nome,email ) values('" + bolsa.getNome() +"' , '"  
					+ bolsa.getEmail() + "' ); ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addDoacaoBanco(Doacao c){
		try {

			banco.executar("insert into doacaoLamp.doacao(nome_bolsista,descricao,id_rotina) values('" + c.getBolsista() +"' , '"  
					+ c.getDescricao() + "' ," + c.getRotina().getId() +"  ); ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validarUsuarioBanco(Usuario t) {
		try {
			banco.conectar();
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select login,senha from doacaoLamp.usuario where login ='"+ t.getLogin()+"' and  senha = '" + t.getSenha()+"' ;");				
			while(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return false;
	}

	@Override
	public boolean verificarUsuarioBanco(Usuario usu){
		try {
			banco.conectar();
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.usuario where login = '" +usu.getLogin()+"';");
			while (rs.next()){ 
				return true;
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}


	@Override
	public boolean verificarBolsistaBanco(Bolsista t){
		try {
			banco.conectar();
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.bolsista where nome = '" +t.getNome()+ "';");
			while (rs.next()){ 

				return true;
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Bolsista recuperarBolsista(String c) {
		try {
			banco.conectar();
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.bolsista where nome = '" +c+ "';");
			while (rs.next()){ 
				b = new Bolsista(rs.getString(1), rs.getNString(2));
				return b;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new Bolsista("","");
	}


	@Override
	public boolean verificarDoacaoBanco(String doa) {
		banco.conectar();
		try {
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.doacao where nome_bolsista = '"+ doa + "';");

			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public ObservableList<Doacao> capiturarTodasDoacoesRotina(double x) {
		ObservableList<Doacao> obs = FXCollections.observableArrayList();
		try {
			banco.conectar();

			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.doacao where id_rotina =" + x  +" ;");

			while (rs.next()){ 
				obs.add( new Doacao(new Bolsista(rs.getString(1), "null"),  rs.getString(2)  , new Rotina(rs.getDouble(3))));

			}
			return obs;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ObservableList<Bolsista> capiturarTodosBolsistas() {
		ObservableList<Bolsista> lista = FXCollections.observableArrayList();
		banco.conectar();
		try {
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.bolsista ;");
			while (rs.next()){ 
				lista.add(new Bolsista(rs.getString(2), rs.getNString(3)));
			}

			return lista;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public void alterarDescricaoDoacao(String nomeBolsista, String descricao, double rotina) {
		try {
			banco.conectar();
			banco.executar("update doacaoLamp.doacao set descricao = '" + descricao +"'  where bolsista ='" +nomeBolsista +"' and rotina='"+rotina+ "' ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Banco getINSTANCE() {
		if (INSTANCE==null){
			INSTANCE = new Banco();
		}
		return INSTANCE;
	}

	@Override
	public ObservableList<String> capiturarIdRotinas() {
		ObservableList<String> obs = FXCollections.observableArrayList();
		try {
			banco.conectar();

			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.rotina;");

			while (rs.next()){ 
				obs.add(""+rs.getDouble(1));

			}
			return obs;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public double getIndiceRotinaAtual() {
		try {
			banco.conectar();

			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select Max(id_rotina) from rotina;");

			while (rs.next()){ 
				return rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public void addRotinaBanco(Rotina rotina) {
		try {
			banco.executar("insert into doacaoLamp.rotina(id_rotina) values('" + rotina.getId() +"' ); ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean excluirBolsista(String nome) {
		try {
			banco.executar("delete from doacaoLamp.bolsista where nome = '" +nome +"' ;");
			banco.executar("delete from doacaoLamp.doacao where nome_bolsista = '" +nome +"'and id_rotina= '"+getIndiceRotinaAtual()+"';");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean excluirUsuario(Usuario usuario) {
		try {
			banco.executar("delete from doacaoLamp.usuario where login = '" +usuario.getLogin() +"' ;");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	
	}

	@Override
	public boolean excluirDoacao(String nome, double rotina) {
		try {
			banco.executar("delete from doacaoLamp.doacao where nome_bolsista = '" +nome +"' ;");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ObservableList<Usuario> capiturarTodosUsuarios() {
		ObservableList<Usuario> lista = FXCollections.observableArrayList();
		banco.conectar();
		try {
			banco.stm = banco.conector.createStatement();
			rs = banco.stm.executeQuery("select * from doacaoLamp.usuario ;");
			while (rs.next()){ 
				lista.add(new Usuario(rs.getString(1), rs.getNString(2)));
			}

			return lista;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public void alterarEmailUsuario(String nomeBolsista, String novoEmail) {
		try {
			banco.conectar();
			banco.executar("update doacaoLamp.bolsista set email = '" + novoEmail +"'  where nome ='" +nomeBolsista +"' ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void alterarSenhaUsuario(String login, String novaSenha) {
		try {
			banco.conectar();
			banco.executar("update doacaoLamp.usuario set senha = '" + novaSenha +"'  where login ='" +login +"' ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}


