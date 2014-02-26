package application;

import javafx.scene.effect.DropShadow;

public class TextField extends javafx.scene.control.TextField{
	
	private DropShadow dropShadow = new DropShadow();
	
	public TextField() {
		setEffect(dropShadow);
	}
}
