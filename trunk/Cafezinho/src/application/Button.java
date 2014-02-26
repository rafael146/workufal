package application;

import javafx.scene.effect.DropShadow;

public class Button extends javafx.scene.control.Button{
	
	private DropShadow dropShadow;
	
	public Button(String nomeBotao) {
		super(nomeBotao);
		dropShadow = new DropShadow();
		getStyleClass().add("buttonGreen");
		setEffect(dropShadow);
	}

}
