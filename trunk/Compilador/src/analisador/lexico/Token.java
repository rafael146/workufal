package analisador.lexico;

public class Token {
	
	protected TokenType tipo;
	protected int linha;
	protected int coluna;
	protected Object valor;
	protected boolean temValor;
	
	
	public Token(TokenType tipo, int linha, int coluna) {
		this.tipo = tipo;
		this.linha = linha;
		this.coluna = coluna;
		temValor = false;
		
	}
	
	public Token(TokenType tipo, int linha, int coluna, Object valor) {
		this.tipo = tipo;
		this.linha = linha;
		this.coluna = coluna;
		this.valor = valor;
		temValor = true;
	}
	
	
	public enum TokenType {
		INTEGER_LITERAL, STRING_LITERAL, 
		ABSTRACT, PRIVATE, PACKAGE, STRICTFP, CONTINUE, VOLATILE, 
		TRANSIENT, INTERFACE, PROTECTED, INSTANCEOF, IMPLEMENTS, 
		SYNCHRONIZED, FINALLY, DEFAULT, EXTENDS, BOOLEAN, PUBLIC, 
		DOUBLE, IMPORT, NATIVE, RETURN, THROWS, SWITCH, STATIC, 
		ASSERT, WHILE, FINAL, FLOAT, CLASS, CONST, CATCH, THROW, 
		SUPER, SHORT, BREAK, VOID, GOTO, LONG, ENUM, ELSE, CHAR, 
		CASE, THIS, BYTE, FOR, INT, NEW, TRY, DO, IF, EOF, 
		BOOLEAN_LITERAL, NULL_LITERAL, FLOATING_POINT_LITERAL, 
		OP_ARITMETICO, PONTO, OP_RELACIONAL, OP_LOGICO, OP_BITWISE, 
		PAREN_ESQ, PAREN_DIR, CHAVE_ESQ, CHAVE_DIR, COLCH_ESQ, 
		COLCH_DIR, PONTO_VIRGULA, VIRGULA, INTERROG, DOIS_PONTOS, 
		INCREMENTO, DECREMENTO, ID, CHARACTER_LITERAL, LAMBDA, ANNOTATION, 
		
	}

}


