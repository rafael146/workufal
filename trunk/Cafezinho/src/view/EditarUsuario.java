package view;

import dados.ddb.DistribuidoDBHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Usuario;
import application.Button;
import application.Label;
import application.Text;
import application.TextField;

public class EditarUsuario extends Stage{
	
	 private TextField textNome;
	 private BorderPane root;
	 private Scene scene;
	 private Text titulo;
	 private Label informacao;
	 private Label nome;
	 private Label senhaAntiga;
	 private Label novaSenha;
	 private Label repetirNovaSenha;
	 private PasswordField textNovaSenha;
	 private PasswordField textRepetirNovaSenha;
	 private PasswordField textSenhaAntiga;
	 private Button atualizar;
	
	public EditarUsuario(Usuario usuario) {
		initComponents();
		
		textNome.setText(usuario.getLogin());
		textNome.setEditable(false);
		scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());
		
		VBox vbox = new VBox(10);
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
		HBox hbox3 = new HBox(30);
		HBox hbox4 = new HBox(30);
		
		hbox1.getChildren().addAll(nome,textNome);
		hbox2.getChildren().addAll(senhaAntiga,textSenhaAntiga);
		hbox3.getChildren().addAll(novaSenha,textNovaSenha);
		hbox4.getChildren().addAll(repetirNovaSenha,textRepetirNovaSenha);

		vbox.getChildren().addAll(titulo,hbox1,hbox2,hbox3,hbox4,atualizar,informacao);
		vbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		hbox3.setAlignment(Pos.CENTER);
		hbox4.setAlignment(Pos.CENTER);
		root.setCenter(vbox);
		
		initModality(Modality.APPLICATION_MODAL);
		setScene(scene);
		show();
		
		atualizar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(DistribuidoDBHelper.getInstance().validarUsuarioBanco(new Usuario(textNome.getText(), textSenhaAntiga.getText()))){
					if(textNovaSenha.getText().equals(textRepetirNovaSenha.getText())){
						DistribuidoDBHelper.getInstance().alterarSenhaUsuario(textNome.getText(),textNovaSenha.getText());
						new Dialog("Senha alterado com sucesso!");
						close();
					}else{
						informacao.setText("As senhas nao correspondem");
					}
				}else{
					informacao.setText("Senha antiga inválida");
				}
				
			}
		});
	}
	
	private void initComponents() {
		root = new BorderPane();
		scene = new Scene(root,500,320,Color.BURLYWOOD);
		titulo = new Text("Alterar Senha Usuario");
		informacao = new Label("");
		nome = new Label("Login:");
		senhaAntiga = new Label("Senha Antiga:");
		repetirNovaSenha = new Label("Repetir:");
		textNome = new TextField();
		novaSenha = new Label("Nova Senha:");
		textNovaSenha = new PasswordField();
		atualizar = new Button("Atualizar");
		textRepetirNovaSenha = new PasswordField();
		textSenhaAntiga = new PasswordField();
	}

}
