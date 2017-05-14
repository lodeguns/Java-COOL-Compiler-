/* The scanner definition for COOL.  */
/* start usercode*/

import java_cup.runtime.Symbol;

 /* end usercode*/
 
%%  

/* Start Options and declarations */


%{  // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    
    // Opened multiline comment parentheses counter
    int parenthesesCounter = 0;
    
    // Prova di compilatori
    boolean fakeflag = false;
 
    // Current line number
    private int curr_lineno = 1;
    
    
    int get_curr_lineno() {
	    return curr_lineno;
    }
    
    
    // AbstractSymbol addString with filename.
    private AbstractSymbol filename;
    void set_filename(String fname) {
	     filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(zzLexicalState) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	case STRING:
		yybegin(YYINITIAL);
		return new Symbol(TokenConstants.ERROR,"EOF in string constant");
	case MLCOMMENT:
		yybegin(YYINITIAL);
		return new Symbol(TokenConstants.ERROR, "EOF in comment");
	case STRING_ERROR:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "EOF in string constant");    
    }
    return new Symbol(TokenConstants.EOF); /* questo solo se usa %cup  sennò dava YYEOF*/
%eofval}

%class CoolLexer /* Il nome della classe che andrà a generare, e che dopo uso in Lexer.java */
%cup
%unicode

/*Regular Expressions*/
NewLineno        = \n|\r|\r\n
WhiteSpace       = [\ \r\v\t\n\f] 
ObjectIdentifier = [a-z]([a-zA-Z0-9_]*)
TypeIdentifier   = [A-Z]([a-zA-Z0-9_]*)
DecimalInt       = ([0-9]+)


/*Additional States*/
%state STRING, STRING_ERROR, QUADCOMMENT, MLCOMMENT, OLCOMMENT

/* End Options and declarations */


%%



/* <STRING_ERROR, STRING, MLCOMMENT, OLCOMMENT> { 
        <<EOF>> {    yybegin(YYINITIAL);
		             return new Symbol(TokenConstants.ERROR, "Modo alternativo di gestire l'eof");} } */

<YYINITIAL>{
          
	{NewLineno} 							{ curr_lineno++;}
              
	/* Keywords */ 
	[cC][lL][aA][sS][sS] 					{ return new Symbol(TokenConstants.CLASS); }
	[iI][fF] 								{ return new Symbol(TokenConstants.IF); }
	[fF][iI] 								{ return new Symbol(TokenConstants.FI); }
	[eE][lL][sS][eE] 						{ return new Symbol(TokenConstants.ELSE); }
	f[aA][lL][sS][eE] 				        { return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.FALSE); }
	t[rR][uU][eE] 						    { return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.TRUE); }
	[iI][nN][hH][eE][rR][iI][tT][sS]        { return new Symbol(TokenConstants.INHERITS); }
	[iI][sS][vV][oO][iI][dD]				{ return new Symbol(TokenConstants.ISVOID); }
	[lL][oO][oO][pP] 						{ return new Symbol(TokenConstants.LOOP); }
	[pP][oO][oO][lL] 						{ return new Symbol(TokenConstants.POOL); }
	[cC][oO][oO][lL]                        { return new Symbol(TokenConstants.POOL); }
	[tT][hH][eE][nN]						{ return new Symbol(TokenConstants.THEN); }
	[iI][nN] 								{ return new Symbol(TokenConstants.IN); }
	[wW][hH][iI][lL][eE] 					{ return new Symbol(TokenConstants.WHILE); }
	[wW][hH][iI][lL][eE] 					{ return new Symbol(TokenConstants.WHILE); }
	[cC][aA][sS][eE]						{ return new Symbol(TokenConstants.CASE); }
	[eE][sS][aA][cC] 						{ return new Symbol(TokenConstants.ESAC); }
	[oO][fF] 								{ return new Symbol(TokenConstants.OF); }
	[nN][oO][tT] 							{ return new Symbol(TokenConstants.NOT); }
	[lL][eE][tT]							{ return new Symbol(TokenConstants.LET); }
	[nN][eE][wW] 							{ return new Symbol(TokenConstants.NEW); }
	/*ObejctID Rules*/
	{ObjectIdentifier} { return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext())); }
	
	/*TypeID Rule*/
	{TypeIdentifier} { return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
	
	/*Integer Rule*/
	{DecimalInt} { return new Symbol(TokenConstants.INT_CONST, AbstractTable.idtable.addString(yytext())); }
		
	/*White Space Rule*/	
	{WhiteSpace} { /*ignore*/ }
	
	
	
	/*Operators*/	
	"+"			 { if(fakeflag)
	               { return new Symbol(TokenConstants.MINUS); }
	               return new Symbol(TokenConstants.PLUS);}
	"-" 		 { if(fakeflag)
	               {return new Symbol(TokenConstants.PLUS);}
	                return new Symbol(TokenConstants.MINUS);}
	"/" 		 { if(fakeflag)
	               {return new Symbol(TokenConstants.MULT);}
	                return new Symbol(TokenConstants.DIV);}
	"*" 		 { if(fakeflag)
	               {return new Symbol(TokenConstants.DIV);}
	               return new Symbol(TokenConstants.MULT);}
	"=" 		 { return new Symbol(TokenConstants.EQ);}
	"<" 		 { return new Symbol(TokenConstants.LT);}
	"." 		 { return new Symbol(TokenConstants.DOT);}
	"<=" 		 { return new Symbol(TokenConstants.LE);}
	"~" 		 { return new Symbol(TokenConstants.NEG);}
	"," 		 { return new Symbol(TokenConstants.COMMA);}
	";" 		 { return new Symbol(TokenConstants.SEMI);}
	":"			 { return new Symbol(TokenConstants.COLON);}
	"(" 		 { return new Symbol(TokenConstants.LPAREN);}
	")" 		 { return new Symbol(TokenConstants.RPAREN);}
	"@" 		 { return new Symbol(TokenConstants.AT);}
	"{" 		 { return new Symbol(TokenConstants.LBRACE);}
	"}" 	 	 { return new Symbol(TokenConstants.RBRACE);}
	"<-" 		 { return new Symbol(TokenConstants.ASSIGN);}
	\"			 { string_buf.setLength(0); yybegin(STRING);}
	"=>" 		 { return new Symbol(TokenConstants.DARROW); }
	
		/*Comments Rules*/	
	
	/*Comments Rules*/
	"(*"		 { parenthesesCounter++; yybegin(MLCOMMENT); }
	"*)"		 { return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }
	"--"		 { curr_lineno++; yybegin(OLCOMMENT); }
	.			 { return new Symbol(TokenConstants.ERROR, yytext()); }
	
}

/*Strings state*/
<STRING>{
	\"						{	yybegin(YYINITIAL);
								if(string_buf.length() == MAX_STR_CONST)
									return new Symbol(TokenConstants.ERROR, "String constant too long");
								return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));
							}
	\000					{ 
								yybegin(STRING_ERROR);
								return new Symbol(TokenConstants.ERROR, "String contains null character."); 
							}			
	\\t						{ string_buf.append('\t'); }
	\\n						{ string_buf.append('\n'); }
	\\b						{ string_buf.append('\b'); }
	\\f						{ string_buf.append('\f'); }
	\\.						{ string_buf.append(yytext().substring(1,yytext().length()));
							  if(string_buf.length() > MAX_STR_CONST){
									yybegin(STRING_ERROR);
									return new Symbol(TokenConstants.ERROR, "String constant too long");
							  }
							}
	\\\n					{ string_buf.append(yytext().substring(1,yytext().length())); 
							  curr_lineno++; 
							  if(string_buf.length() > MAX_STR_CONST){
									yybegin(STRING_ERROR);
									return new Symbol(TokenConstants.ERROR, "String constant too long");
							  }
							}
	\n 						{ curr_lineno++; yybegin(YYINITIAL); return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); }
	"(*"					{ string_buf.append("(*"); }
	"*)"					{ string_buf.append("*)"); }
	"--"					{ string_buf.append("--"); }
	
	. 						{ // previously, the regexp here was [^\\\n\"\0]+
                              // the problem is that inside [] jlex doesn't recognize
                              // metacharacters like \n or \0, so this doesn't do the
                              // right thing.  so while it's inefficient, a correct
                              // implementation is obtained with '.'
								string_buf.append(yytext()); 
 	 							if(string_buf.length() > MAX_STR_CONST ){
 	 								yybegin(STRING_ERROR);
    								return new Symbol(TokenConstants.ERROR, "String constant too long");
  								}
							}
}

<STRING_ERROR>{ 
	\" 					{ yybegin(YYINITIAL); }
	\n 					{ curr_lineno++; yybegin(YYINITIAL); }
	\\\n				{ curr_lineno++;}
	\\. | .				{}
}


/*Multiline comments state*/
<MLCOMMENT>{
	\n						{ curr_lineno++; }
	"(*" 					{  parenthesesCounter++;}
	"*)"				    { parenthesesCounter--; if(parenthesesCounter == 0) yybegin(YYINITIAL);}
	.                       {}
}



/*Oneline comments state*/
<OLCOMMENT>{
	\n|\r|\r\n				{ yybegin(YYINITIAL); }
	.						{}
}

/*Invalid lessema rule*/
.                           { return new Symbol(TokenConstants.ERROR, yytext()); }     
