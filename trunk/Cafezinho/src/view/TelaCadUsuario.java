package view;

import dados.ddb.DistribuidoDBHelper;
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
import application.Text;
import application.TextField;

public class TelaCadUsuario extends VBox{
	
	private TextField textNome;
	private PasswordField textSenha;
	private PasswordField textSenha2;
	private Label login;
	private Label senha;
	private Label repetirSenha;
	private Label informacao;
	private Text titulo;
	private Button cadastrar;
	private Button sair;
	
	public TelaCadUsuario() {
		initComponets();
		layoutizacao();
		
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
		HBox hbox3 = new HBox(30);
		HBox hboxRepetirSenha = new HBox(30);

		getChildren().add(titulo);
		
		hbox1.getChildren().addAll(login,textNome);
		hbox2.getChildren().addAll(senha,textSenha);
		hboxRepetirSenha.getChildren().addAll(repetirSenha,textSenha2);
				
		hbox3.getChildren().addAll(cadastrar,sair);
		getChildren().addAll(hbox1,hbox2,hboxRepetirSenha,hbox3,informacao);
		
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		hbox3.setAlignment(Pos.CENTER);
		hboxRepetirSenha.setAlignment(Pos.CENTER);
		
		cadastrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(textSenha.getText().equals(textSenha2.getText())){
					Usuario fulano = new Usuario(textNome.getText(),textSenha.getText());
					if(DistribuidoDBHelper.getInstance().verificarUsuarioBanco(fulano)){
						informacao.setText("Esse usuário já existe");
					}else{
						DistribuidoDBHelper.getInstance().addUsuarioBanco(fulano);
						//mensagem informativa
						new Dialog("Usuário Cadastrado");
						limparCampos();
					}
				}else{
					informacao.setText("As senhas não correspondem");
				}
				
			}
		});
		
		sair.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Scene tela = (Scene) getScene();
				tela.translacaoTelaDirEsq(tela.getTelaTable());
				limparCampos();
				
			}
		});
	}
	
	private void layoutizacao() {
		setAlignment(Pos.CENTER);
		setSpacing(20);
		
	}

	private void limparCampos(){
		textNome.setText("");
		textSenha.setText("");
		textSenha2.setText("");
		informacao.setText("");
	}

	private void initComponets(){
		login = new Label("Nome");
		senha = new Label("Senha");
		repetirSenha= new Label("Repita");
		informacao = new Label("");
		titulo = new Text("Cadastrar Usuário");
		cadastrar = new Button("Cadastrar");
		sair = new Button("Sair");
		textNome = new TextField();
		textSenha = new PasswordField();
		textSenha2 = new PasswordField();
	
	}
}
