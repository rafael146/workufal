package view;

import dados.ddb.DistribuidoDBHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Bolsista;
import model.Doacao;
import model.Rotina;
import application.Button;
import application.Label;

public class TelaIniciarRotacao extends Stage{
	
	private ListView<Bolsista> listaBolsista;
	private ListView<Bolsista> listaDoacoes;
	private Bolsista x = null;
	private BorderPane root;
	private Scene scene;
	private Button iniciarRotina;
	private Button passar;
	private Button voltar;
	private Button passarTudo;
	private Button voltarTudo;
	private Label titulo;
	
	public TelaIniciarRotacao(final TelaTable tabela) {
		
		initComponents();
		
		scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());
		HBox hbox = new HBox(10);
		VBox vbox1 = new VBox(20);
		VBox vbox2 = new VBox(20);
		VBox vbox3 = new VBox(20);
		hbox.getChildren().addAll(vbox1,vbox2,vbox3);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(0,0,15,0));
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(titulo,hbox,iniciarRotina);
		vbox.setPadding(new Insets(10,10,10,10));
		root.setCenter(vbox);
		
		vbox1.getChildren().add(listaDoacoes);
		vbox2.getChildren().addAll(passar,voltar,passarTudo,voltarTudo);
		
		listaBolsista.setItems(DistribuidoDBHelper.getInstance().capiturarTodosBolsistas());
		vbox3.getChildren().add(listaBolsista);
		
		setScene(scene);
		initModality(Modality.APPLICATION_MODAL);
		show();
		
		passar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
					x = listaBolsista.getSelectionModel().getSelectedItem();
					if(x!=null){
						listaDoacoes.getItems().add(x);
						listaBolsista.getItems().remove(x);
					}
					listaBolsista.getSelectionModel().select(null);
			}
		});
		
		voltar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
					x = listaDoacoes.getSelectionModel().getSelectedItem();
					if(x!=null){
						listaBolsista.getItems().add(x);
						listaDoacoes.getItems().remove(x);
					}
					listaDoacoes.getSelectionModel().select(null);
				
			}
		});
		
		passarTudo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
					listaBolsista.getItems().clear();
					listaDoacoes.setItems(DistribuidoDBHelper.getInstance().capiturarTodosBolsistas());
				
			}
		});
		
		voltarTudo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				listaDoacoes.getItems().clear();
				listaBolsista.setItems(DistribuidoDBHelper.getInstance().capiturarTodosBolsistas());
			
				
			}
		});
		
		iniciarRotina.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				DistribuidoDBHelper.getInstance().addRotinaBanco(new Rotina());
				ObservableList<Bolsista> bolsistas = listaDoacoes.getItems();
				for (Bolsista bolsista : bolsistas) {
					DistribuidoDBHelper.getInstance().addDoacaoBanco(new Doacao(bolsista,"",new Rotina(DistribuidoDBHelper.getInstance().getIndiceRotinaAtual())));
				}
				tabela.atualizarTabela();
				close();
			}
		});
	}
	
	private void initComponents(){
		root = new BorderPane();
		scene = new Scene(root,580,300,Color.BURLYWOOD);
		iniciarRotina = new Button("Iniciar Rotina");
		listaDoacoes = new ListView<Bolsista>();
		passar = new Button("<<");
		voltar = new Button(">>");
		passarTudo = new Button(" |< ");
		voltarTudo = new Button(" >| ");
		listaBolsista = new ListView<Bolsista>();
		titulo = new Label("Na Rotina                                  "
				+ "Bolsistas");
	}
}
