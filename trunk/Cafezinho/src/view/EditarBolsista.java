package view;

import dados.ddb.DistribuidoDBHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Bolsista;
import application.Button;
import application.Label;
import application.Text;
import application.TextField;

public class EditarBolsista extends Stage{
	
	 private TextField textNome;
	 private BorderPane root;
	 private Scene scene;
	 private Text titulo;
	 private Label informacao;
	 private Label nome;
	 private Label descricao;
	 private TextField textDescricao;
	 private Button atualizar;
	
	public EditarBolsista(final Bolsista bolsista) {
		initComponents();
		
		textNome.setText(bolsista.getNome());
		textNome.setEditable(false);
		textDescricao.setText(bolsista.getEmail());
		scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());
		
		VBox vbox = new VBox(30);
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
		
		hbox1.getChildren().addAll(nome,textNome);
		
		hbox2.getChildren().addAll(descricao,textDescricao);

		vbox.getChildren().addAll(titulo,hbox1,hbox2,atualizar,informacao);
		vbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		root.setCenter(vbox);
		
		initModality(Modality.APPLICATION_MODAL);
		setScene(scene);
		show();
		
		atualizar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				DistribuidoDBHelper.getInstance().alterarEmailUsuario(textNome.getText(), textDescricao.getText());
				new Dialog("Bolsista alterado com sucesso!");
				bolsista.setEmail(textDescricao.getText());
				close();
			}
		});
	}
	
	private void initComponents() {
		root = new BorderPane();
		scene = new Scene(root,500,320,Color.BURLYWOOD);
		titulo = new Text("Atualizar Email Usuario");
		informacao = new Label("");
		nome = new Label("Nome:");
		textNome = new TextField();
		descricao = new Label("Email:");
		textDescricao = new TextField();
		atualizar = new Button("Atualizar");
	}

}
