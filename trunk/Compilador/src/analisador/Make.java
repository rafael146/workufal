package analisador;

import java.io.File;


public class Make {
	
	public static void main(String[] args) {		
		jflex.Main.generate(new File("src/analisador/lexico/Lexer.flex"));
	}
	
}
