package view;

import java.awt.Toolkit;

import application.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Bolsista;
import model.Doacao;
import dados.ddb.DistribuidoDBHelper;

public class TelaTable extends VBox{
	
	private int WIDTH;
	private ObservableList<Doacao> data;
	private TableView<Doacao> tableView;
	private ImageView botaoNovaRotacao;
	private ImageView botaoEditarRotacao;
	private double idRotinaAtual;
	private ComboBox<String> comboBox;
	private Button botaoAlterar;
	private Button botaoSair;
	private Label titulo;
	
	@SuppressWarnings("unchecked")
	public TelaTable() {
		initComponents();
		layoutizacao();
		atualizarTabela();
		
		final DropShadow dropShadow = new DropShadow();
		
		tableView.setEditable(true);
		tableView.setEffect(dropShadow);
		
		TableColumn<Doacao, String> idColumn = new TableColumn<>("  Bolsista");
		idColumn.setCellValueFactory(new PropertyValueFactory<Doacao,String>("bolsista"));
		idColumn.setMinWidth(WIDTH/2);
		TableColumn<Doacao, String> nameColumn = new TableColumn<>("  Doação");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Doacao,String>("descricao"));
		nameColumn.setMinWidth(WIDTH/2);
		tableView.getColumns().addAll(idColumn, nameColumn);
		tableView.setFocusTraversable(false);
		
		
		titulo.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,20));
        titulo.setEffect(dropShadow);
		titulo.setFont(new Font(30));
		
		HBox boxButton = new HBox(20);
		boxButton.setPadding(new Insets(0, 30, 0, 30));

		HBox boximage = new HBox(30);
		boximage.getChildren().addAll(botaoEditarRotacao,botaoNovaRotacao);
		boximage.setAlignment(Pos.CENTER_RIGHT);
		boxButton.getChildren().addAll(botaoAlterar, botaoSair);
		boximage.setPadding(new Insets(10,30,30,10));
		
		ObservableList<String> id_rotinas = DistribuidoDBHelper.getInstance().capiturarIdRotinas();
		comboBox.setItems(id_rotinas);
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String teste = comboBox.getSelectionModel().getSelectedItem();
				if(teste!=null){
					double id_rotina = Double.parseDouble(teste);
					atualizarTabela(id_rotina);
					if(id_rotina!=idRotinaAtual){
						botaoAlterar.setDisable(true);
						botaoEditarRotacao.setDisable(true);
					}
					else{ 
						botaoAlterar.setDisable(false);
						botaoEditarRotacao.setDisable(false);
					}
				}
			}
		});
		comboBox.setMinWidth(200);
		comboBox.getStyleClass().add("buttonGreen");
		comboBox.setEffect(dropShadow);
		
		VBox boxTable = new VBox(30);
		boxTable.setPadding(new Insets(0, 20, 0, 20));
		boxTable.getChildren().addAll(comboBox,tableView);
		boxTable.setOpacity(0.75);
	
		atualizarTabela(idRotinaAtual);
		
		botaoSair.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//sair
			}
		});
		
		final TelaTable x = this;
		botaoAlterar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (tableView.getSelectionModel().getSelectedItem()!=null){
					Bolsista nomeBolsista = tableView.getSelectionModel().getSelectedItem().getBolsista();
					new TelaRegistarDoacao(nomeBolsista.getNome(),x);
				}
				
			}
		});
		
		botaoNovaRotacao.setEffect(dropShadow);
        botaoNovaRotacao.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				botaoNovaRotacao.setOpacity(0.5);
				
			}
		});
        
        botaoNovaRotacao.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				botaoNovaRotacao.setOpacity(1);
				
			}
		});
        
        botaoNovaRotacao.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new TelaIniciarRotacao(x);
				
			}
		});
        
        //here
        
		
		botaoEditarRotacao.setEffect(dropShadow);
        botaoEditarRotacao.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				botaoEditarRotacao.setOpacity(0.5);
				
			}
		});
        
        botaoEditarRotacao.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				botaoEditarRotacao.setOpacity(1);
				
			}
		});
        
        botaoEditarRotacao.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new TelaEditarRotacao(x);
				
			}
		});
        
        getChildren().addAll(titulo,boxTable,boxButton,boximage);
	}
	
	private void atualizarTabela(double x){
		data=DistribuidoDBHelper.getInstance().capiturarTodasDoacoesRotina(x);
		tableView.setItems(data);
		comboBox.setItems(DistribuidoDBHelper.getInstance().capiturarIdRotinas());
	}
	
	public void atualizarTabela(){
		idRotinaAtual = DistribuidoDBHelper.getInstance().getIndiceRotinaAtual();
		if(idRotinaAtual==0) botaoEditarRotacao.setVisible(false);
		else botaoEditarRotacao.setVisible(true);
		atualizarTabela(idRotinaAtual);
	}
	
	private void initComponents(){
		titulo = new Label("Relação de Bolsistas");
		WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width-80;
		data = FXCollections.observableArrayList();;
		tableView = new TableView<Doacao>();
		botaoNovaRotacao = new ImageView("./imagens/cafeicone.png");
		botaoEditarRotacao = new ImageView("./imagens/editar.png");
		comboBox = new ComboBox<>();
		botaoAlterar = new Button("Alterar");
		botaoSair = new Button("Sair");
		idRotinaAtual = DistribuidoDBHelper.getInstance().getIndiceRotinaAtual();
	}
	
	private void layoutizacao(){
		setAlignment(Pos.TOP_CENTER);
		setPadding(new Insets(20,0,0,0));
		setSpacing(20);
	}
}
