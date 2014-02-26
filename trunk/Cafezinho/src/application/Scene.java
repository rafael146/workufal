package application;

import model.Usuario;
import view.MenuApp;
import view.TelaCadBolsista;
import view.TelaCadUsuario;
import view.TelaListaUsuario;
import view.TelaListarBolsistas;
import view.TelaLogin;
import view.TelaTable;
import javafx.animation.Animation;
import javafx.animation.TranslateTransitionBuilder;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class Scene extends javafx.scene.Scene{
	
	private Usuario usuario;
	private Node telaPrincipal;
	private MenuApp menuApp;
	private TelaLogin telaLogin;
	private TelaTable telaTable;
	private TelaCadBolsista telaCadBolsista;
	private Animation transicao;
	private TelaCadUsuario telaCadUsuario;
	private TelaListarBolsistas telaListaBolsistas;
	private TelaListaUsuario telaListaUsuarios;
	
	public Scene(Parent root, double width, double height, Paint fill) {
		super(root, width, height, fill);
		
		initComponents();
		ocutarTelas();
	}

	public MenuApp getMenuApp() {
		return menuApp;
	}

	public TelaLogin getTelaLogin() {
		return telaLogin;
	}

	public TelaTable getTelaTable() {
		return telaTable;
	}

	public Node getTelaPrincipal() {
		return telaPrincipal;
	}
	
	public TelaCadBolsista getTelaCadBolsista() {
		return telaCadBolsista;
	}
	
	public TelaCadUsuario getTelaCadUsuario() {
		return telaCadUsuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public TelaListarBolsistas getTelaListaBolsistas() {
		return telaListaBolsistas;
	}

	public TelaListaUsuario getTelaListaUsuarios() {
		return telaListaUsuarios;
	}
	
	private void transicao(Node node,int x,int y){
		transicao = TranslateTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .node(node)
                .fromX(x)
                .toX(y)
                .autoReverse(true)
                .build();
		transicao.play(); 
	}
	
	public void translacaoTelaDirEsq(Node node){
		transicao(telaPrincipal,0,-1400);
		transicao(node,2000,0);
		telaPrincipal=node;
	}
	
	public void translacaoTelaEsqDir(Node node){
		transicao(telaPrincipal,0,2000);
		transicao(node,-1400,0);
		telaPrincipal=node;
	}
	
	private void initComponents(){
		menuApp = new MenuApp();
		menuApp.setVisible(false);
		telaLogin = new TelaLogin();
		usuario=null;
		telaPrincipal = telaLogin;
		telaTable = new TelaTable();
		telaCadBolsista = new TelaCadBolsista();
		telaCadUsuario = new TelaCadUsuario();
		telaListaBolsistas = new TelaListarBolsistas();
		telaListaUsuarios = new TelaListaUsuario();
	}
	
	private void ocutarTelas() {
		transicao(telaTable, 0, 2000);
		transicao(telaCadBolsista, 0, 2000);
		transicao(telaCadUsuario, 0, 2000);
		transicao(telaListaBolsistas, 0, 2000);
		transicao(telaListaUsuarios, 0, 2000);
		
	}

	public Node getAllTelas() {
		// TODO Auto-generated method stub
		return null;
	}
}
