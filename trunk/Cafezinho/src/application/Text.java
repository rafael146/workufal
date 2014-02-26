package application;

import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Text extends javafx.scene.text.Text{
	
	private DropShadow dropShadow = new DropShadow();
	
	public Text(String texto) {
		super(texto);
		setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL,40));
		setEffect(dropShadow);
	}
}
