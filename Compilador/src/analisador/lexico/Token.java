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
		IDENTIFIER, INTEGER_LITERAL, STRING_LITERAL, 
		ABSTRACT, PRIVATE, PACKAGE, STRICTFP, CONTINUE, VOLATILE, 
		TRANSIENT, INTERFACE, PROTECTED, INSTANCEOF, IMPLEMENTS, 
		SYNCHRONIZED, FINALLY, DEFAULT, EXTENDS, BOOLEAN, PUBLIC, 
		DOUBLE, IMPORT, NATIVE, RETURN, THROWS, SWITCH, STATIC, 
		ASSERT, WHILE, FINAL, FLOAT, CLASS, CONST, CATCH, THROW, 
		SUPER, SHORT, BREAK, VOID, GOTO, LONG, ENUM, ELSE, CHAR, 
		CASE, THIS, BYTE, FOR, INT, NEW, TRY, DO, IF, IGUAL, MAIS, 
		IGUALS, EOF, BOOLEAN_LITERAL, NULL_LITERAL, DIV, MULT, FLOATING_POINT_LITERAL, DOT, MINUS, LPAREN, RPAREN, LBRACE, RBRACE, LBRACK, RBRACK, SEMICOLON, COMMA, EQ, GT, LT, NOT, COMP, QUESTION, COLON, AND, OR, PLUS, XOR, MOD, DIVEQ, MULTEQ, MINUSMINUS, MINUSEQ, EQEQ, GTEQ, RSHIFT, LTEQ, LSHIFT, NOTEQ, ANDEQ, ANDAND, OREQ, OROR, PLUSEQ, PLUSPLUS, XOREQ, MODEQ, RSHIFTEQ, URSHIFT, LSHIFTEQ, CHARACTER_LITERAL, URSHIFTEQ, 
		
	}

}


