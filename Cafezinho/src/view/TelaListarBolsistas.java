package view;

import dados.ddb.DistribuidoDBHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Bolsista;
import application.Button;
import application.Scene;
import application.Text;

public class TelaListarBolsistas extends VBox{
	
	private ListView<Bolsista> listaBolsista;
	private Text titulo;
	private Button botaoAlterar;
	private Button botaoExcluir;
	private Button sair;
	
	public TelaListarBolsistas() {
		initComponents();
		layoutizacao();
		
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(0,10,0,10));
		listaBolsista.setOpacity(0.75);
		hbox.getChildren().addAll(botaoAlterar, botaoExcluir, sair);
		getChildren().addAll(titulo,listaBolsista,hbox);
		
		botaoAlterar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(listaBolsista.getSelectionModel().getSelectedItem()!=null)
					new EditarBolsista(listaBolsista.getSelectionModel().getSelectedItem());
				
			}
		});
		
		botaoExcluir.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				DistribuidoDBHelper.getInstance().excluirBolsista(listaBolsista.getSelectionModel().getSelectedItem().getNome());
				atualizarLista();
				Scene tela = (Scene) getScene();
				tela.getTelaTable().atualizarTabela();
			}

		});
		
		sair.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Scene tela = (Scene) getScene();
				tela.translacaoTelaEsqDir(tela.getTelaTable());
				
			}
		});
	}
	
	private void initComponents(){
		listaBolsista = new ListView<>();
		titulo = new Text("Lista de Bolsistas");
		botaoAlterar = new Button("Alterar");
		botaoExcluir = new Button("Excluir");
		sair = new Button("Sair");
	}
	
	private void layoutizacao(){
		setSpacing(30);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(100,300,100,300));
	}
	
	public void atualizarLista() {
		listaBolsista.setItems(DistribuidoDBHelper.getInstance().capiturarTodosBolsistas());
		
	}
}
