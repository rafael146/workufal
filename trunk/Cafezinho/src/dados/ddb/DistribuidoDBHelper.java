package dados.ddb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Bolsista;
import model.Doacao;
import model.Rotina;
import model.Usuario;
import dados.ConsultasBanco;

/*
 * Todos os métodos para inserir ou alterar dados só são usados no banco local
 * 
 * Consultas são usadas em todos os bancos disponíveis.
 * 
 */
public class DistribuidoDBHelper implements ConsultasBanco {

	Database local;
	List<Database> distribuidos;

	private DistribuidoDBHelper() {
		// TODO mover dados de banco local e distribuidos para um arquivo de
		// configuração
		local = new Database("root", "root", "doacaoLamp");
		distribuidos = new LinkedList<>();
		distribuidos.add(local);
		distribuidos.add(new Database("root", "root", "doacaoLamp2",
				"jdbc:mysql://localhost:3306/"));
		for (Database database : distribuidos) {
			try {
				database.criarBanco();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//addUsuarioBanco(new Usuario("admin", "admin"));
	}

	public void addDatabase(Database d) {
		distribuidos.add(d);
	}
		
	@Override
	public void addUsuarioBanco(Usuario usuario) {
		local.executar("insert into usuario( login , senha) values('"
				+ usuario.getLogin() + "' , '" + usuario.getSenha() + "' ); ");

	}

	@Override
	public void addBolsistaBanco(Bolsista bolsa) {
		local.executar("insert into bolsista(nome,email ) values('"
				+ bolsa.getNome() + "' , '" + bolsa.getEmail() + "' ); ");

	}

	@Override
	public void addRotinaBanco(Rotina rotina) {
		local.executar("insert into rotina(id_rotina) values('"
				+ rotina.getId() + "' ); ");

	}

	@Override
	public boolean validarUsuarioBanco(Usuario t) {
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select login,senha from usuario where login ='"
							+ t.getLogin() + "' and  senha = '" + t.getSenha()
							+ "' ;");
					ResultSet rs = st.executeQuery()) {
				if(rs.next()) return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean verificarUsuarioBanco(Usuario t) {
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select login,senha from usuario where login ='"
							+ t.getLogin() + "' and  senha = '" + t.getSenha() + "' ;");
					ResultSet rs = st.executeQuery()) {
				if(rs.next()) return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean verificarBolsistaBanco(Bolsista t) {
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from bolsista where nome = '" +t.getNome()+ "';");
					ResultSet rs = st.executeQuery()) {
				if(rs.next()) return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean verificarDoacaoBanco(String doa) {
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from doacao where nome_bolsista = '"+ doa + "';");
					ResultSet rs = st.executeQuery()) {
				if(rs.next()) return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void addDoacaoBanco(Doacao c) {
		local.executar("insert into doacao(nome_bolsista,descricao,id_rotina) values('" + c.getBolsista() +"' , '"  
				+ c.getDescricao() + "' ," + c.getRotina().getId() +"  ); ");

	}

	@Override
	public Bolsista recuperarBolsista(String c) {
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from bolsista where nome = '" +c+ "';");
					ResultSet rs = st.executeQuery()) {	
				if (rs.next()){ 
					Bolsista b = new Bolsista(rs.getString(2), rs.getNString(3));
					return b;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new Bolsista("", "");
	}

	@Override
	public ObservableList<Doacao> capiturarTodasDoacoesRotina(double x) {
		ObservableList<Doacao> obs = FXCollections.observableArrayList();
		for (Database database : distribuidos) {
			
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from doacao where id_rotina =" + x  +" ;");
					ResultSet rs = st.executeQuery()) {			
				while(rs.next()){ 
					obs.add( new Doacao(new Bolsista(rs.getString(1), "null"),  rs.getString(2)  , new Rotina(rs.getDouble(3))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return obs;
	}

	@Override
	public ObservableList<Bolsista> capiturarTodosBolsistas() {
		ObservableList<Bolsista> lista = FXCollections.observableArrayList();
		for (Database database : distribuidos) {
			
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from bolsista ;");
					ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					lista.add(new Bolsista(rs.getString(2), rs.getNString(3)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	@Override
	public ObservableList<Usuario> capiturarTodosUsuarios() {
		ObservableList<Usuario> lista = FXCollections.observableArrayList();
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from usuario ;");
					ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					lista.add(new Usuario(rs.getString(1), rs.getNString(2)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	@Override
	public void alterarDescricaoDoacao(String nomeBolsista, String descricao,
			double rotina) {
		for (Database database : distribuidos) {
			database.executar("update doacao set descricao = '" + descricao +"'  where nome_bolsista ='" +nomeBolsista +"' and id_rotina ="+rotina+ " ;");
		}
	}

	@Override
	public void alterarEmailUsuario(String nomeBolsista, String novoEmail) {
		for (Database database : distribuidos) {
			database.executar("update bolsista set email = '" + novoEmail +"'  where nome ='" +nomeBolsista +"' ;");
		}
	}

	@Override
	public void alterarSenhaUsuario(String login, String novaSenha) {
		for (Database database : distribuidos) {
			database.executar("update usuario set senha = '" + novaSenha +"'  where login ='" +login +"' ;");
		}
	}

	@Override
	public ObservableList<String> capiturarIdRotinas() {
		ObservableList<String> obs = FXCollections.observableArrayList();
		for (Database database : distribuidos) {
			
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select * from rotina;");
					ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					obs.add(""+rs.getDouble(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return obs;
	}
	
	//observação: rotinas com primarykey duplicadas nos banco distribuido warning!
	@Override
	public double getIndiceRotinaAtual() {
		double max=0;
		for (Database database : distribuidos) {
			try(Connection con = database.getConnection(); 
					PreparedStatement st = con.prepareStatement("select Max(id_rotina) from rotina;");
					ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					if(max < rs.getDouble(1)){
						max = rs.getDouble(1);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return max;
	}

	@Override
	public boolean excluirBolsista(String nome) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirDoacao(String nome, double rotina) {
		for (Database database : distribuidos) {
			database.executar("delete from doacao where nome_bolsista = '" +nome +"' and id_rotina =" +rotina+ " ;");
		}
		return true;
	}

	public static DistribuidoDBHelper getInstance() {
		return SinglentonHolder.INSTANCE;
	}

	private static class SinglentonHolder {
		private static DistribuidoDBHelper INSTANCE = new DistribuidoDBHelper();
	}

}
