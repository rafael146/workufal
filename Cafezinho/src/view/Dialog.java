package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Button;
import application.Label;

public class Dialog extends Stage{
	
	public Dialog(String aviso) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,500,100,Color.ALICEBLUE);
		//scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		
		VBox vbox = new VBox(10);
		HBox boximage = new HBox(20);
		
		Label texto = new Label(aviso);
		texto.setFont(new Font(18));
		//Image image = new Image("./imagens/coche_antiguo.gif");
		
        boximage.getChildren().addAll(texto);
        boximage.setAlignment(Pos.CENTER);
        
        Button ok = new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
        
        vbox.getChildren().addAll(boximage,ok);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20,20,20,20));
        
        root.setCenter(vbox);
		setScene(scene);
		setOpacity(0.9);
		initModality(Modality.APPLICATION_MODAL);
		show();
		
	}
}
