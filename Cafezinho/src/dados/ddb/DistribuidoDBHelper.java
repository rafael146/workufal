package dados.ddb;

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
		distribuidos.add(new Database("root", "root", "doacaoLamp",
				"jdbc:mysql://localhost:3306/"));
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
			try (ResultSet rs = database
					.query("select login,senha from usuario where login ='"
							+ t.getLogin() + "' and  senha = '" + t.getSenha()
							+ "' ;")) {
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
			try (ResultSet rs = database
					.query("select login,senha from usuario where login ='"
							+ t.getLogin() + "' and  senha = '" + t.getSenha() + "' ;")) {
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
			try (ResultSet rs = database.query("select * from bolsista where nome = '" +t.getNome()+ "';")) {
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
			try (ResultSet rs = database.query("select * from doacao where nome_bolsista = '"+ doa + "';")) {
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
			try (ResultSet rs = database.query("select * from bolsista where nome = '" +c+ "';")) {			
				if (rs.next()){ 
					Bolsista b = new Bolsista(rs.getString(1), rs.getNString(2));
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
			try (ResultSet rs = database.query("select * from doacao where id_rotina =" + x  +" ;")) {			
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
			try (ResultSet rs = database
					.query("select * from bolsista ;")) {
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
			try (ResultSet rs = database.query("select * from bolsista ;")) {
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
			database.executar("update doacao set descricao = '" + descricao +"'  where bolsista ='" +nomeBolsista +"' and rotina='"+rotina+ "' ;");
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
			database.executar("update doacaoLamp.usuario set senha = '" + novaSenha +"'  where login ='" +login +"' ;");
		}
	}

	@Override
	public ObservableList<String> capiturarIdRotinas() {
		ObservableList<String> obs = FXCollections.observableArrayList();
		for (Database database : distribuidos) {
			try (ResultSet rs = database.query("select * from rotina;")) {
				while (rs.next()) {
					obs.add(""+rs.getDouble(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return obs;
	}
	
	//observação: rotinas com primarykey duplicadas no banco distribuido warning!
	@Override
	public double getIndiceRotinaAtual() {
		try (ResultSet rs = local.query("select Max(id_rotina) from rotina;")) {
			if (rs.next()) {
				return rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
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
	public boolean excluirDoacao(String nome) {
		// TODO Auto-generated method stub
		return false;
	}

	public static DistribuidoDBHelper getInstance() {
		return SinglentonHolder.INSTANCE;
	}

	private static class SinglentonHolder {
		private static DistribuidoDBHelper INSTANCE = new DistribuidoDBHelper();
	}

}
