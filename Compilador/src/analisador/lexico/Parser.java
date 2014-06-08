package analisador.lexico;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import analisador.lexico.Token.TokenType;

public class Parser {

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("teste.java");
			Lexer lexer = new Lexer(fr);
			Token tk;
			FileWriter out = new FileWriter("out.token");
			while(true) {
				tk = lexer.nextToken();
				if(tk.tipo == TokenType.EOF)
					break;
				out.write(tk.tipo + " ");
			}
			out.flush();
			out.close();
			System.out.println("Parsed Complete!!!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
