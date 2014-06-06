package analisador.lexico;

import analisador.lexico.Token.TokenType;

%%
%class Lexer
%unicode
%type Token
%line
%column
%function nextToken

%{
    StringBuffer string = new StringBuffer();

    private Token token(TokenType type) {
        return new Token(type, yyline+1, yycolumn+1);
    }
    
    private Token token(TokenType type, Object value) {
        return new Token(type, yyline+1, yycolumn+1, value);
    }
%}

LineTerminator       = \r|\n|\r\n
InputCharacter       = [^\r\n]
WhiteSpace           = {LineTerminator} | [ \t\f]

/* comentários */
Comment              = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*
Identifier           = [:jletter:] [:jletterdigit:]*
DecIntegerLiteral    = 0 | [1-9][0-9]*

%state STRING

%%

<YYINITIAL> { 

/* palavras reservadas */
  "abstract"                     { return token(TokenType.ABSTRACT); }
  "assert"                       { return token(TokenType.ASSERT); }
  "boolean"                      { return token(TokenType.BOOLEAN); }
  "break"                        { return token(TokenType.BREAK); }
  "byte"                         { return token(TokenType.BYTE); }
  "case"                         { return token(TokenType.CASE); }
  "catch"                        { return token(TokenType.CATCH); }
  "char"                         { return token(TokenType.CHAR); }
  "class"                        { return token(TokenType.CLASS); }
  "const"                        { return token(TokenType.CONST); }
  "continue"                     { return token(TokenType.CONTINUE); }
  "default"                      { return token(TokenType.DEFAULT); }
  "do"                           { return token(TokenType.DO); }
  "double"                       { return token(TokenType.DOUBLE); }
  "else"                         { return token(TokenType.ELSE); }
  "enum"                         { return token(TokenType.ENUM); }
  "extends"                      { return token(TokenType.EXTENDS); }
  "final"                        { return token(TokenType.FINAL); }
  "finally"                      { return token(TokenType.FINALLY); }
  "float"                        { return token(TokenType.FLOAT); }
  "for"                          { return token(TokenType.FOR); }
  "goto"                         { return token(TokenType.GOTO); }
  "if"                           { return token(TokenType.IF); }
  "implements"                   { return token(TokenType.IMPLEMENTS); }
  "import"                       { return token(TokenType.IMPORT); }
  "instanceof"                   { return token(TokenType.INSTANCEOF); }
  "int"                          { return token(TokenType.INT); }
  "interface"                    { return token(TokenType.INTERFACE); }
  "long"                         { return token(TokenType.LONG); }
  "native"                       { return token(TokenType.NATIVE); }
  "new"                          { return token(TokenType.NEW); }
  "public"                       { return token(TokenType.PUBLIC); }
  "short"                        { return token(TokenType.SHORT); }
  "super"                        { return token(TokenType.SUPER); }
  "switch"                       { return token(TokenType.SWITCH); }
  "synchronized"                 { return token(TokenType.SYNCHRONIZED); }
  "package"                      { return token(TokenType.PACKAGE); }
  "private"                      { return token(TokenType.PRIVATE); }
  "protected"                    { return token(TokenType.PROTECTED); }
  "transient"                    { return token(TokenType.TRANSIENT); }
  "return"                       { return token(TokenType.RETURN); }
  "void"                         { return token(TokenType.VOID); }
  "static"                       { return token(TokenType.STATIC); }
  "while"                        { return token(TokenType.WHILE); }
  "this"                         { return token(TokenType.THIS); }
  "throw"                        { return token(TokenType.THROW); }
  "throws"                       { return token(TokenType.THROWS); }
  "try"                          { return token(TokenType.TRY); }
  "volatile"                     { return token(TokenType.VOLATILE); }
  "strictfp"                     { return token(TokenType.STRICTFP); }

  /* boolean */
  "true"                         { return token(TokenType.BOOLEAN_LITERAL, true); }
  "false"                        { return token(TokenType.BOOLEAN_LITERAL, false); }
  
  /* null */
  "null"                         { return token(TokenType.NULL_LITERAL); }
  
  /* literals */
  {DecIntegerLiteral}     { return token(TokenType.INTEGER_LITERAL); }

  /* identificador */
  {Identifier}            { return token(TokenType.IDENTIFIER); }
   
  \"                      { string.setLength(0); yybegin(STRING); }

   /* operadores */
   "="                     { return token(TokenType.IGUAL); }
   "=="                    { return token(TokenType.IGUALS); }
   "+"                     { return token(TokenType.MAIS); }
    
    /* comentários */
    {Comment}               { /* ignore */ }

    /* espaço */
    {WhiteSpace}            { /* ignore */ }
}

<STRING> {
    \"                      { yybegin(YYINITIAL);
                              return token(TokenType.STRING_LITERAL,
                              string.toString()); }
    [^\n\r\"\\]+            { string.append( yytext() ); }
    \\t                     { string.append('\t'); }
    \\n                     { string.append('\n'); }
    \\r                     { string.append('\r'); }
    \\\"                    { string.append('\"'); }
    \\                      { string.append('\\'); }
}

/* error fallback */
[^]                         { throw new Error("Illegal character <"+yytext()+">"); }

<<EOF>>                     { return token(TokenType.EOF); }