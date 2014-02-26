package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Bolsista;
import application.Button;
import application.Label;
import application.Scene;
import application.Text;
import application.TextField;
import dados.Banco;

public class TelaCadBolsista extends VBox{
	
	private TextField textNome;
	private TextField textEmail;
	private Label login;
	private Label email;
	private Label informacao;
	private Button cadastrar;
	private Button sair;
	private Text titulo;
	
	public TelaCadBolsista() {
		initComponents();
		layoutizacao();
		
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
		HBox hbox3 = new HBox(30);

		getChildren().add(titulo);
		
		hbox1.getChildren().addAll(login,textNome);
		hbox2.getChildren().addAll(email,textEmail);
		
		hbox3.getChildren().addAll(cadastrar,sair);
		getChildren().addAll(hbox1,hbox2,hbox3,informacao);
		
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		hbox3.setAlignment(Pos.CENTER);
		
		cadastrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Bolsista fulano = new Bolsista(textNome.getText().toUpperCase(), textEmail.getText());
				if(Banco.getINSTANCE().verificarBolsistaBanco(fulano)){
					informacao.setText("Esse bolsista já existe no banco de dados!");
				}else if(fulano.getNome()==""){
					informacao.setText("Nome em branco");
				}else{
					Banco.getINSTANCE().addBolsistaBanco(fulano);
					new Dialog("Bolsista "+textNome.getText()+" cadastrado(a)!");
					limparCampos();
				
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
	
	private void limparCampos() {
		textNome.setText("");
		textEmail.setText("");
		informacao.setText("");
		
	}
	
	private void initComponents(){
		login = new Label("Nome");
		email = new Label("E-mail");
		informacao = new Label("");
		textNome = new TextField();
		textEmail = new TextField();
		cadastrar = new Button("Cadastrar");
		sair = new Button("Sair");
		titulo = new Text("Cadastrar Bolsista");
	}
	
	private void layoutizacao(){
		setAlignment(Pos.CENTER);
		setSpacing(30);
	}
}
