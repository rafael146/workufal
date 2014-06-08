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
    
    private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
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
DecLongLiteral    = {DecIntegerLiteral} [lL]

HexIntegerLiteral = 0 [xX] 0* {HexDigit} {1,8}
HexLongLiteral    = 0 [xX] 0* {HexDigit} {1,16} [lL]
HexDigit          = [0-9a-fA-F]

OctIntegerLiteral = 0+ [1-3]? {OctDigit} {1,15}
OctLongLiteral    = 0+ 1? {OctDigit} {1,21} [lL]
OctDigit          = [0-7]

FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+


StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]


%state STRING, CHARLITERAL

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
  
  /* separadores */
  "("                            { return token(TokenType.LPAREN); }
  ")"                            { return token(TokenType.RPAREN); }
  "{"                            { return token(TokenType.LBRACE); }
  "}"                            { return token(TokenType.RBRACE); }
  "["                            { return token(TokenType.LBRACK); }
  "]"                            { return token(TokenType.RBRACK); }
  ";"                            { return token(TokenType.SEMICOLON); }
  ","                            { return token(TokenType.COMMA); }
  "."                            { return token(TokenType.DOT); }
  
  /* operadores */
  "="                            { return token(TokenType.EQ); }
  ">"                            { return token(TokenType.GT); }
  "<"                            { return token(TokenType.LT); }
  "!"                            { return token(TokenType.NOT); }
  "~"                            { return token(TokenType.COMP); }
  "?"                            { return token(TokenType.QUESTION); }
  ":"                            { return token(TokenType.COLON); }
  "=="                           { return token(TokenType.EQEQ); }
  "<="                           { return token(TokenType.LTEQ); }
  ">="                           { return token(TokenType.GTEQ); }
  "!="                           { return token(TokenType.NOTEQ); }
  "&&"                           { return token(TokenType.ANDAND); }
  "||"                           { return token(TokenType.OROR); }
  "++"                           { return token(TokenType.PLUSPLUS); }
  "--"                           { return token(TokenType.MINUSMINUS); }
  "+"                            { return token(TokenType.PLUS); }
  "-"                            { return token(TokenType.MINUS); }
  "*"                            { return token(TokenType.MULT); }
  "/"                            { return token(TokenType.DIV); }
  "&"                            { return token(TokenType.AND); }
  "|"                            { return token(TokenType.OR); }
  "^"                            { return token(TokenType.XOR); }
  "%"                            { return token(TokenType.MOD); }
  "<<"                           { return token(TokenType.LSHIFT); }
  ">>"                           { return token(TokenType.RSHIFT); }
  ">>>"                          { return token(TokenType.URSHIFT); }
  "+="                           { return token(TokenType.PLUSEQ); }
  "-="                           { return token(TokenType.MINUSEQ); }
  "*="                           { return token(TokenType.MULTEQ); }
  "/="                           { return token(TokenType.DIVEQ); }
  "&="                           { return token(TokenType.ANDEQ); }
  "|="                           { return token(TokenType.OREQ); }
  "^="                           { return token(TokenType.XOREQ); }
  "%="                           { return token(TokenType.MODEQ); }
  "<<="                          { return token(TokenType.LSHIFTEQ); }
  ">>="                          { return token(TokenType.RSHIFTEQ); }
  ">>>="                         { return token(TokenType.URSHIFTEQ); }
  
  \"                             { string.setLength(0); yybegin(STRING); }
  \'                             { yybegin(CHARLITERAL); }
  
  /* literals */
  
  "-2147483648"                  { return token(TokenType.INTEGER_LITERAL, new Integer(Integer.MIN_VALUE)); }
  {DecLongLiteral}               { return token(TokenType.INTEGER_LITERAL, new Long(yytext().substring(0,yylength()-1))); }
  
  {HexIntegerLiteral}            { return token(TokenType.INTEGER_LITERAL, new Integer((int) parseLong(2, yylength(), 16))); }
  {HexLongLiteral}               { return token(TokenType.INTEGER_LITERAL, new Long(parseLong(2, yylength()-1, 16))); }
 
  {OctIntegerLiteral}            { return token(TokenType.INTEGER_LITERAL, new Integer((int) parseLong(0, yylength(), 8))); }  
  {OctLongLiteral}               { return token(TokenType.INTEGER_LITERAL, new Long(parseLong(0, yylength()-1, 8))); }
  
  {FloatLiteral}                 { return token(TokenType.FLOATING_POINT_LITERAL, new Float(yytext().substring(0,yylength()-1))); }
  {DoubleLiteral}                { return token(TokenType.FLOATING_POINT_LITERAL, new Double(yytext())); }
  {DoubleLiteral}[dD]            { return token(TokenType.FLOATING_POINT_LITERAL, new Double(yytext().substring(0,yylength()-1))); }
    
  /* comentários */
  {Comment}               { /* ignore */ }

  /* espaço */
  {WhiteSpace}            { /* ignore */ }

  /* identificador */
  {Identifier}            { return token(TokenType.IDENTIFIER); }
}


<STRING> {
  \"                             { yybegin(YYINITIAL); return token(TokenType.STRING_LITERAL, string.toString()); }
  
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                                           string.append( val ); }
  
  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}

<CHARLITERAL> {
  {SingleCharacter}\'            { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, yytext().charAt(0)); }
  
  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\b');}
  "\\t"\'                        { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\t');}
  "\\n"\'                        { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\n');}
  "\\f"\'                        { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\f');}
  "\\r"\'                        { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\r');}
  "\\\""\'                       { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\"');}
  "\\'"\'                        { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\'');}
  "\\\\"\'                       { yybegin(YYINITIAL); return token(TokenType.CHARACTER_LITERAL, '\\'); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL); 
                                          int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                        return token(TokenType.CHARACTER_LITERAL, (char)val); }
  
  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated character literal at end of line"); }
}

/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return token(TokenType.EOF); }