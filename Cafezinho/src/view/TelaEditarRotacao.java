package view;

import java.util.ArrayList;

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
import dados.Banco;

public class TelaEditarRotacao extends Stage{
	
	private ListView<Bolsista> listaBolsista;
	private ListView<Bolsista> listaDoacoes;
	private Bolsista x = null;
	private BorderPane root;
	private Scene scene;
	private Button atualizarRotina;
	private Button passar;
	private Button voltar;
	private Button passarTudo;
	private Button voltarTudo;
	private Label titulo;
	ArrayList<Bolsista> bolsistasNaRotina;
	
	public TelaEditarRotacao(final TelaTable tabela) {
		
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
		vbox.getChildren().addAll(titulo,hbox,atualizarRotina);
		vbox.setPadding(new Insets(10,10,10,10));
		root.setCenter(vbox);
		
		vbox1.getChildren().add(listaDoacoes);
		vbox2.getChildren().addAll(passar,voltar,passarTudo,voltarTudo);
		
		atualizarListas();
		
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
					listaDoacoes.setItems(Banco.getINSTANCE().capiturarTodosBolsistas());
				
			}
		});
		
		voltarTudo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				listaDoacoes.getItems().clear();
				listaBolsista.setItems(Banco.getINSTANCE().capiturarTodosBolsistas());
			
				
			}
		});
		
		atualizarRotina.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				for (Bolsista temp : listaDoacoes.getItems()) {
					if(!Banco.getINSTANCE().verificarDoacaoBanco(temp.getNome()))
						Banco.getINSTANCE().addDoacaoBanco(new Doacao(temp, "", new Rotina(Banco.getINSTANCE().getIndiceRotinaAtual())));
				}
				for (Bolsista temp : listaBolsista.getItems()) {
					if(Banco.getINSTANCE().verificarDoacaoBanco(temp.getNome()))
						Banco.getINSTANCE().excluirDoacao(temp.getNome());
				}
				tabela.atualizarTabela();
				close();
			}
		});
	}
	
	private void initComponents(){
		root = new BorderPane();
		scene = new Scene(root,580,300,Color.BURLYWOOD);
		atualizarRotina = new Button("Atualizar Rotina");
		listaDoacoes = new ListView<Bolsista>();
		passar = new Button("<<");
		voltar = new Button(">>");
		passarTudo = new Button(" |< ");
		voltarTudo = new Button(" >| ");
		listaBolsista = new ListView<Bolsista>();
		titulo = new Label("Na Rotina                                  "
				+ "Bolsistas");
		bolsistasNaRotina = new ArrayList<>();
	}
	
	private void atualizarListas(){
		listaBolsista.setItems(Banco.getINSTANCE().capiturarTodosBolsistas());
		for (Doacao doacao : Banco.getINSTANCE().capiturarTodasDoacoesRotina(Banco.getINSTANCE().getIndiceRotinaAtual())) {
			for (Bolsista bolsista : listaBolsista.getItems()) {
				if(doacao.getBolsista().getNome().equals(bolsista.getNome())){
					bolsistasNaRotina.add(bolsista);
				}
			}
		}
		for (Bolsista bolsista : bolsistasNaRotina) {
			listaDoacoes.getItems().add(bolsista);
			listaBolsista.getItems().remove(bolsista);
		}
	}
}
