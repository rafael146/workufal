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
import application.TextField;

public class TelaRegistarDoacao extends Stage{
	
	 private TextField textNome;
	 private TelaTable tabela;
	 private BorderPane root;
	 private Scene scene;
	 private Label titulo;
	 private Label informacao;
	 private Label nome;
	 private Label descricao;
	 private TextField textDescricao;
	 private Button registrar;
	 private Bolsista doador;
	
	public TelaRegistarDoacao() {
		initComponents();
		scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());
		
		VBox vbox = new VBox(30);
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
		
		hbox1.getChildren().addAll(nome,textNome);
		
		hbox2.getChildren().addAll(descricao,textDescricao);

		vbox.getChildren().addAll(titulo,hbox1,hbox2,registrar,informacao);
		vbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		root.setCenter(vbox);
		
		initModality(Modality.APPLICATION_MODAL);
		setScene(scene);
		show();
		
		registrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				doador = DistribuidoDBHelper.getInstance().recuperarBolsista(textNome.getText());
				//System.out.println(doador.getNome());
				if(doador.getNome().equals("")){
					informacao.setText("Esse usuario nao existe no banco de dados!");
				}else{
					String nomeBolsista = doador.getNome().toUpperCase();
					String descricao = textDescricao.getText().toUpperCase();
					DistribuidoDBHelper.getInstance().alterarDescricaoDoacao(nomeBolsista,descricao,DistribuidoDBHelper.getInstance().getIndiceRotinaAtual());
					tabela.atualizarTabela();
					close();
					
				}
			}
		});
	}
	
	private void initComponents() {
		root = new BorderPane();
		scene = new Scene(root,500,320,Color.BURLYWOOD);
		titulo = new Label("Registrar Doação");
		informacao = new Label("");
		nome = new Label("Nome:");
		textNome = new TextField();
		descricao = new Label("Descrição:");
		textDescricao = new TextField();
		registrar = new Button("Registrar");
	}

	public TelaRegistarDoacao(String x, TelaTable tabela){
		this();
		textNome.setText(x);
		textNome.setEditable(false);
		this.tabela = tabela;
	}
}
