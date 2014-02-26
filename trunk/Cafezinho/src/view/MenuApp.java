package view;

import application.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class MenuApp extends MenuBar{
	
	private Scene tela;
	private Menu arquivoMenu;
	private Menu cadastrarMenu;
	private Menu editarMenu;
	private Menu graficoMenu;
	private Menu sobreMenu;
	private MenuItem sairItem;
	private MenuItem logoutComoItem;
	private MenuItem imprimirItem;
	private MenuItem cadastrarBolsistaItem;
	private MenuItem cadastrarUsuarioItem;
	private MenuItem pizzaChart;
	private MenuItem barraChart;
	private MenuItem lineChart;
	private MenuItem sobreMenuItem;
	private MenuItem listarBolsistaItem;
	private MenuItem listarUsuarioItem;
	
	public MenuApp(){
		initComponets();
		
		getMenus().addAll(arquivoMenu,cadastrarMenu,editarMenu,graficoMenu,sobreMenu);
		
		arquivoMenu.getItems().addAll(logoutComoItem,imprimirItem,sairItem);
		cadastrarMenu.getItems().addAll(cadastrarUsuarioItem,cadastrarBolsistaItem);
		editarMenu.getItems().addAll(listarBolsistaItem,listarUsuarioItem);
		sobreMenu.getItems().add(sobreMenuItem);
		graficoMenu.getItems().addAll(pizzaChart,barraChart,lineChart);
		sobreMenuItem.setAccelerator(KeyCombination.keyCombination("F1"));
		
		cadastrarBolsistaItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tela = (Scene) getScene();
				tela.translacaoTelaEsqDir(tela.getTelaCadBolsista());
				
			}
		});
		
		cadastrarUsuarioItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tela = (Scene) getScene();
				tela.translacaoTelaEsqDir(tela.getTelaCadUsuario());
				
			}
		});
		
		logoutComoItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tela = (Scene) getScene();
				tela.getMenuApp().setVisible(false);
				tela.setUsuario(null);
				tela.translacaoTelaEsqDir(tela.getTelaLogin());
				
			}
		});
		
		sobreMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//getScene().setRoot(new TelaSobre());
				
			}
		});
		
		lineChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//getScene().setRoot(new TelaLineChart("Eixox","Eixoy"));
				
			}
		});
		
		barraChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//getScene().setRoot(new TelaBarChart("Eixox", "Eixoy"));
				
			}
		});
		
		pizzaChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//getScene().setRoot(new TelaPieChart());
				
			}
		});
		
		listarBolsistaItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tela = (Scene) getScene();
				tela.translacaoTelaDirEsq(tela.getTelaListaBolsistas());
				tela.getTelaListaBolsistas().atualizarLista();
				
			}
		});
		
		listarUsuarioItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tela = (Scene) getScene();
				tela.translacaoTelaDirEsq(tela.getTelaListaUsuarios());
				tela.getTelaListaUsuarios().atualizarLista();
				
			}
		});
	}
	
	private void initComponets(){
		arquivoMenu = new Menu("Arquivo");
		cadastrarMenu = new Menu("Cadastro");
		editarMenu = new Menu("Editar");
		graficoMenu = new Menu("Grafico");
		sobreMenu = new Menu("Sobre");
		
		sairItem = new MenuItem("Sair");
		logoutComoItem = new MenuItem("Fazer Logout");
		imprimirItem = new MenuItem("Imprimir relatório");
		cadastrarBolsistaItem = new MenuItem("Bolsista");
		cadastrarUsuarioItem = new MenuItem("Usuario");
		pizzaChart = new MenuItem("Pizza");
		barraChart = new MenuItem("Barra");
		lineChart = new MenuItem("Linha");
		sobreMenuItem = new MenuItem("Sobre");
		listarBolsistaItem = new MenuItem("Lista Bolsista");
		listarUsuarioItem = new MenuItem("Lista Usuario");
	}
}
