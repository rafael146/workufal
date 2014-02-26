package application;
	
import java.sql.SQLException;

import model.Usuario;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import dados.Banco;
import dados.Database;

public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1200,700,Color.MOCCASIN);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			StackPane panel = new StackPane();
			ImageView cafezinho = new ImageView("./imagens/cafezinho.gif");
			cafezinho.setScaleX(3);
			cafezinho.setScaleY(3);
			panel.getChildren().add(cafezinho);
			panel.getChildren().addAll(scene.getTelaLogin(),scene.getTelaTable()
					,scene.getTelaCadBolsista(),scene.getTelaCadUsuario(),
					scene.getTelaListaBolsistas(),scene.getTelaListaUsuarios());
			//panel.getChildren().addAll(scene.getAllTelas());
			root.setCenter(panel);
			root.setTop(scene.getMenuApp());
			
			primaryStage.setScene(scene);
			primaryStage.setOpacity(0.98);
			primaryStage.setTitle("Doação LAMP");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		new Database().criarBanco();
		new Banco().addUsuarioBanco(new Usuario("admin", "admin"));
		launch(args);
	}
	
	
}
