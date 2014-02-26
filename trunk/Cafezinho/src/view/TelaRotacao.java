package view;

import javafx.animation.Animation;
import javafx.animation.RotateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TelaRotacao extends BorderPane{
	
	private Animation animacao_rotate;
	ImageView imageview = new ImageView("./imagens/Java-WebStart.png");
	
	public void animacaoRot(){
		if (animacao_rotate!=null){
			animacao_rotate.stop();
		}
		
		animacao_rotate = RotateTransitionBuilder.create()
							.node(imageview)
							.fromAngle(0).toAngle(180)
							.duration(Duration.seconds(0.5))
							.onFinished(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
							
									getScene().setRoot(new TelaTable());								
								}
							})
							.build();
		animacao_rotate.play();
		 
	}
	
	public TelaRotacao() {
		VBox topmenu = new VBox(30);
		MenuApp menu = new MenuApp();
		topmenu.getChildren().add(menu);
		setTop(topmenu);
		
		VBox boximage = new VBox();
		Label titulo = new Label("Iniciar Rotação");
		titulo.setFont(new Font(30));
		
        boximage.getChildren().addAll(imageview,titulo);
        boximage.setAlignment(Pos.CENTER);
        //setCenter(boximage);
        
        imageview.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				animacaoRot();
			}
		});
        
        imageview.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				imageview.setOpacity(0.5);
				
			}
		});
        
        imageview.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				imageview.setOpacity(1);
				
			}
		});
        
        StackPane panel = new StackPane();
		ImageView cafezinho = new ImageView("./imagens/cafezinho.gif");
		cafezinho.setScaleX(3);
		cafezinho.setScaleY(3);
		panel.getChildren().addAll(cafezinho,boximage);
		setCenter(panel);
	}
	
}
