package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Usuario;
import application.Button;
import application.Label;
import application.Scene;
import application.TextField;
import dados.Banco;

public class TelaLogin extends VBox{
	
	private Label login = new Label("Login");
	private Label senha = new Label("Senha");
	private Label informacao;
	private TextField textLogin;
	private PasswordField textSenha;
	private Button entrar;
	
	public TelaLogin(){
		initComponents();
		layoutizacao();
		
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
        
		hbox1.getChildren().addAll(login,textLogin);
		hbox2.getChildren().addAll(senha,textSenha);
		
		getChildren().addAll(hbox1,hbox2,entrar,informacao);
		
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		
		entrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Usuario paraLogar = new Usuario(textLogin.getText(),textSenha.getText());
				if(Banco.getINSTANCE().validarUsuarioBanco(paraLogar)){
					Scene tela = (Scene) getScene();
					tela.getMenuApp().setVisible(true);
					tela.setUsuario(paraLogar);
					tela.translacaoTelaDirEsq(tela.getTelaTable());
					limparCampos();
				}else{
					informacao.setText("Usuario ou senha inválido!");
				}
			}

			private void limparCampos() {
				textLogin.setText("");
				textSenha.setText("");
			}
		});
		
	}

	private void layoutizacao() {
		setSpacing(20);
		setAlignment(Pos.CENTER);
	}

	private void initComponents() {
		informacao = new Label("");
		textLogin = new TextField();
		textSenha = new PasswordField();
		entrar = new Button("Entrar");
		
	}

}
