package analisador.lexico;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import analisador.lexico.Token.TokenType;


/*
 * 
 * Carregar um arquivo fonte por console ou arquivos
 * 
 * Gerar Código tokenizado
 */

public class Scanner {
	
	private File file;
	private File output;

	public Scanner(File file, File toSave) {
		this.file = file;
		this.output = toSave;
	}
	
	public void parse() throws IOException {
			FileReader fr = new FileReader(file);
			Lexer lexer = new Lexer(fr);
			Token tk;
			FileWriter out = new FileWriter(output);
			while(true) {
				tk = lexer.nextToken();
				if(tk.tipo == TokenType.EOF)
					break;
				out.write(tk.tipo + " ");
			}
			fr.close();
			out.flush();
			out.close();
	}
}
