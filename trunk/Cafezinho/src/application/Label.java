package application;

import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Label extends javafx.scene.control.Label{
	
	private DropShadow dropShadow = new DropShadow();
	
	public Label(String titulo) {
		super(titulo);
		setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL,20));
		setEffect(dropShadow);
	}

}
