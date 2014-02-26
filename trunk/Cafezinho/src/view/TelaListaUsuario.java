package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Usuario;
import application.Button;
import application.Scene;
import application.Text;
import dados.ddb.DistribuidoDBHelper;

public class TelaListaUsuario extends VBox{
	
	private ListView<Usuario> listaUsuario;
	private Text titulo;
	private Button sair;
	private Button botaoAlterar;
	private Button botaoExcluir;
	
	public TelaListaUsuario() {
		initComponents();
		layoutizacao();
		
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(0,10,0,10));
		listaUsuario.setItems(DistribuidoDBHelper.getInstance().capiturarTodosUsuarios());
		listaUsuario.setOpacity(0.75);
		hbox.getChildren().addAll(botaoAlterar,botaoExcluir,sair);
		getChildren().addAll(titulo,listaUsuario,hbox);
		
		botaoAlterar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(listaUsuario.getSelectionModel().getSelectedItem()!=null)
					new EditarUsuario(listaUsuario.getSelectionModel().getSelectedItem());
				
			}
		});
		
		botaoExcluir.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DistribuidoDBHelper.getInstance().excluirUsuario(listaUsuario.getSelectionModel().getSelectedItem());
				atualizarLista();
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
		listaUsuario = new ListView<>();
		titulo = new Text("Lista de Usuarios");
		sair = new Button("Sair");
		botaoAlterar = new Button("Alterar");
		botaoExcluir = new Button("Excluir");
	}
	
	private void layoutizacao(){
		setSpacing(30);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(100,300,100,300));
	}
	
	public void atualizarLista() {
		listaUsuario.setItems(DistribuidoDBHelper.getInstance().capiturarTodosUsuarios());
		
	}
}
