package dados;

import javafx.collections.ObservableList;
import model.Bolsista;
import model.Doacao;
import model.Rotina;
import model.Usuario;

public interface ConsultasBanco {

	public void addUsuarioBanco(Usuario t);
	public void addBolsistaBanco(Bolsista t);
	public void addRotinaBanco(Rotina t);
	public  boolean validarUsuarioBanco(Usuario t);
	public  boolean verificarUsuarioBanco(Usuario t);
	public  boolean verificarBolsistaBanco(Bolsista t);
	public  boolean verificarDoacaoBanco(String nomeDoBolsista);
	public  void addDoacaoBanco(Doacao c);
	public Bolsista recuperarBolsista(String c);
	public ObservableList<Doacao> capiturarTodasDoacoesRotina(double c);
	public ObservableList<Bolsista> capiturarTodosBolsistas();
	public ObservableList<Usuario> capiturarTodosUsuarios();
	public void alterarDescricaoDoacao(String nomeBolsista,String descricao, double rotina);
	public void alterarEmailUsuario(String nomeBolsista, String novoEmail);
	public void alterarSenhaUsuario(String login, String novaSenha);
	public ObservableList<String> capiturarIdRotinas();
	public double getIndiceRotinaAtual();
	public boolean excluirBolsista(String nome);
	public boolean excluirUsuario(Usuario usuario);
	public boolean excluirDoacao(String nome,double rotina);

}
