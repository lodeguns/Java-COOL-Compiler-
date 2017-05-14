/* The following code was generated by JFlex 1.6.1 */

/* The scanner definition for COOL.  */
/* start usercode*/

import java_cup.runtime.Symbol;

 /* end usercode*/
 

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1
 * from the specification file <tt>cool.lex</tt>
 */
class CoolLexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;
  public static final int STRING_ERROR = 4;
  public static final int QUADCOMMENT = 6;
  public static final int MLCOMMENT = 8;
  public static final int OLCOMMENT = 10;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5, 5
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\1\77\10\0\1\3\1\1\1\75\1\76\1\2\22\0\1\3\1\0"+
    "\1\73\5\0\1\66\1\67\1\56\1\53\1\63\1\54\1\61\1\55"+
    "\12\10\1\65\1\64\1\60\1\57\1\74\1\0\1\70\1\12\1\7"+
    "\1\20\1\46\1\14\1\11\1\7\1\41\1\27\1\7\1\13\1\22"+
    "\1\7\1\15\1\16\1\50\1\7\1\34\1\25\1\42\1\36\1\43"+
    "\1\52\3\7\1\0\1\100\2\0\1\6\1\0\1\23\1\101\1\17"+
    "\1\45\1\31\1\30\1\4\1\40\1\26\2\4\1\21\1\4\1\37"+
    "\1\44\1\47\1\4\1\33\1\24\1\32\1\35\1\5\1\51\3\4"+
    "\1\71\1\0\1\72\1\62\6\0\1\75\u1fa2\0\1\75\1\75\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\udfe6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\6\0\1\1\2\2\1\3\1\4\1\5\1\6\4\5"+
    "\1\4\1\5\1\4\1\5\1\4\1\5\4\4\1\5"+
    "\2\4\1\5\1\4\1\5\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\3\30"+
    "\1\32\1\33\1\30\1\34\1\35\1\36\3\34\1\36"+
    "\1\5\1\37\5\5\1\40\3\4\3\5\2\4\2\5"+
    "\1\41\1\42\1\4\1\41\1\42\1\5\1\4\1\37"+
    "\6\4\1\5\1\40\1\4\1\5\1\43\1\44\1\45"+
    "\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55"+
    "\1\56\1\57\1\60\1\61\1\62\1\63\3\5\1\64"+
    "\1\5\1\65\3\4\3\5\1\66\1\4\1\66\1\5"+
    "\2\4\2\5\5\4\1\64\1\65\1\5\1\4\1\5"+
    "\1\67\1\70\1\71\1\5\1\72\1\73\1\4\1\72"+
    "\1\73\1\5\2\74\2\4\2\5\1\4\1\70\1\71"+
    "\1\75\2\76\1\4\2\5\2\77\2\4\2\5\1\100"+
    "\2\101\1\102\1\4\1\103\1\5\1\103\1\4\1\5"+
    "\2\104";

  private static int [] zzUnpackAction() {
    int [] result = new int[191];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\102\0\204\0\306\0\u0108\0\u014a\0\u018c\0\u018c"+
    "\0\u01ce\0\u018c\0\u0210\0\u0252\0\u0294\0\u02d6\0\u0318\0\u035a"+
    "\0\u039c\0\u03de\0\u0420\0\u0462\0\u04a4\0\u04e6\0\u0528\0\u056a"+
    "\0\u05ac\0\u05ee\0\u0630\0\u0672\0\u06b4\0\u06f6\0\u0738\0\u077a"+
    "\0\u07bc\0\u018c\0\u07fe\0\u018c\0\u0840\0\u0882\0\u08c4\0\u018c"+
    "\0\u018c\0\u018c\0\u018c\0\u018c\0\u0906\0\u018c\0\u018c\0\u018c"+
    "\0\u018c\0\u018c\0\u018c\0\u018c\0\u0948\0\u098a\0\u09cc\0\u018c"+
    "\0\u018c\0\u0a0e\0\u018c\0\u018c\0\u018c\0\u0a50\0\u0a92\0\u0ad4"+
    "\0\u0b16\0\u0b58\0\u0252\0\u0b9a\0\u0bdc\0\u0c1e\0\u0c60\0\u0ca2"+
    "\0\u0252\0\u0ce4\0\u0d26\0\u0d68\0\u0daa\0\u0dec\0\u0e2e\0\u0e70"+
    "\0\u0eb2\0\u0ef4\0\u0f36\0\u0210\0\u0f78\0\u0fba\0\u0252\0\u0ffc"+
    "\0\u103e\0\u1080\0\u0210\0\u10c2\0\u1104\0\u1146\0\u1188\0\u11ca"+
    "\0\u120c\0\u124e\0\u0210\0\u1290\0\u12d2\0\u018c\0\u018c\0\u018c"+
    "\0\u018c\0\u018c\0\u018c\0\u018c\0\u018c\0\u018c\0\u018c\0\u018c"+
    "\0\u018c\0\u018c\0\u018c\0\u018c\0\u018c\0\u018c\0\u1314\0\u1356"+
    "\0\u1398\0\u0252\0\u13da\0\u0252\0\u141c\0\u145e\0\u14a0\0\u14e2"+
    "\0\u1524\0\u1566\0\u0210\0\u15a8\0\u0252\0\u15ea\0\u162c\0\u166e"+
    "\0\u16b0\0\u16f2\0\u1734\0\u1776\0\u17b8\0\u17fa\0\u183c\0\u0210"+
    "\0\u0210\0\u187e\0\u18c0\0\u1902\0\u0252\0\u0252\0\u0252\0\u1944"+
    "\0\u0210\0\u0210\0\u1986\0\u0252\0\u0252\0\u19c8\0\u0210\0\u0252"+
    "\0\u1a0a\0\u1a4c\0\u1a8e\0\u1ad0\0\u1b12\0\u0210\0\u0210\0\u0210"+
    "\0\u0210\0\u0252\0\u1b54\0\u1b96\0\u1bd8\0\u0210\0\u0252\0\u1c1a"+
    "\0\u1c5c\0\u1c9e\0\u1ce0\0\u0210\0\u0210\0\u0252\0\u0252\0\u1d22"+
    "\0\u0210\0\u1d64\0\u0252\0\u1da6\0\u1de8\0\u0210\0\u0252";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[191];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\7\1\10\1\11\1\12\2\13\1\7\1\14\1\15"+
    "\1\16\2\14\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\2\13\1\14\1\26\1\27\1\30\1\31\1\32"+
    "\1\13\1\14\1\13\1\14\1\33\1\13\1\14\1\34"+
    "\1\14\1\35\1\13\1\14\1\36\1\37\1\40\1\41"+
    "\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51"+
    "\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61"+
    "\1\62\1\7\1\0\1\12\2\7\1\13\1\63\1\64"+
    "\1\0\51\63\1\65\1\63\1\66\7\63\1\67\4\63"+
    "\1\70\1\63\2\0\1\71\1\72\1\63\1\73\1\74"+
    "\1\0\70\73\1\75\1\73\2\0\1\73\1\76\1\73"+
    "\1\7\2\0\72\7\2\0\3\7\1\73\1\10\1\0"+
    "\53\73\1\77\7\73\1\100\6\73\2\0\4\73\1\75"+
    "\1\101\72\73\2\0\3\73\103\0\1\10\104\0\47\13"+
    "\26\0\1\13\4\0\47\14\26\0\1\14\10\0\1\15"+
    "\75\0\6\14\1\102\13\14\2\103\23\14\26\0\1\14"+
    "\4\0\15\14\2\104\1\14\2\105\25\14\26\0\1\14"+
    "\4\0\10\14\1\106\1\14\1\107\12\14\1\106\12\14"+
    "\1\110\6\14\26\0\1\14\4\0\5\14\1\111\16\14"+
    "\1\111\22\14\26\0\1\14\4\0\6\13\1\112\3\13"+
    "\1\113\2\13\2\114\1\112\20\13\1\113\6\13\26\0"+
    "\1\13\4\0\6\14\1\115\3\14\1\116\2\14\2\117"+
    "\1\115\20\14\1\116\6\14\26\0\1\14\4\0\10\13"+
    "\1\120\1\13\1\121\12\13\1\120\12\13\1\121\6\13"+
    "\26\0\1\13\4\0\10\14\1\122\1\14\1\123\12\14"+
    "\1\122\12\14\1\123\6\14\26\0\1\14\4\0\5\13"+
    "\1\124\3\13\1\125\6\13\2\126\2\13\1\124\6\13"+
    "\1\125\13\13\26\0\1\13\4\0\5\14\1\127\3\14"+
    "\1\130\6\14\2\131\2\14\1\127\6\14\1\130\13\14"+
    "\26\0\1\14\4\0\6\13\1\132\10\13\1\132\2\13"+
    "\2\133\23\13\26\0\1\13\4\0\15\13\2\134\1\13"+
    "\2\135\25\13\26\0\1\13\4\0\27\13\2\136\3\13"+
    "\2\137\11\13\26\0\1\13\4\0\10\13\1\140\1\13"+
    "\1\141\12\13\1\140\12\13\1\141\6\13\26\0\1\13"+
    "\4\0\34\14\2\142\11\14\26\0\1\14\4\0\5\13"+
    "\1\143\16\13\1\143\22\13\26\0\1\13\4\0\12\13"+
    "\1\113\25\13\1\113\6\13\26\0\1\13\4\0\12\14"+
    "\1\116\25\14\1\116\6\14\26\0\1\14\4\0\34\13"+
    "\2\144\11\13\26\0\1\13\4\0\34\14\2\145\11\14"+
    "\26\0\1\14\54\0\1\146\114\0\1\147\106\0\1\150"+
    "\61\0\1\151\2\0\1\152\100\0\1\153\77\0\1\154"+
    "\114\0\1\155\70\0\1\156\23\0\1\157\1\160\1\0"+
    "\25\157\1\161\1\157\1\162\4\157\1\163\35\157\2\0"+
    "\2\157\1\164\1\73\1\10\1\0\72\73\2\0\3\73"+
    "\67\0\1\165\70\0\1\166\24\0\1\75\104\0\7\14"+
    "\1\167\37\14\26\0\1\14\4\0\20\14\2\170\25\14"+
    "\26\0\1\14\4\0\6\14\1\171\10\14\1\171\27\14"+
    "\26\0\1\14\4\0\45\14\2\172\26\0\1\14\4\0"+
    "\5\14\1\173\20\14\1\174\7\14\1\174\10\14\26\0"+
    "\1\14\4\0\26\14\1\174\7\14\1\174\10\14\26\0"+
    "\1\14\4\0\20\13\2\175\25\13\26\0\1\13\4\0"+
    "\12\13\1\176\25\13\1\176\6\13\26\0\1\13\4\0"+
    "\6\13\1\177\10\13\1\177\27\13\26\0\1\13\4\0"+
    "\20\14\2\200\25\14\26\0\1\14\4\0\12\14\1\201"+
    "\25\14\1\201\6\14\26\0\1\14\4\0\6\14\1\202"+
    "\10\14\1\202\27\14\26\0\1\14\4\0\26\13\1\203"+
    "\7\13\1\203\10\13\26\0\1\13\4\0\12\13\1\204"+
    "\25\13\1\204\6\13\26\0\1\13\4\0\26\14\1\205"+
    "\7\14\1\205\10\14\26\0\1\14\4\0\12\14\1\206"+
    "\25\14\1\206\6\14\26\0\1\14\4\0\34\13\2\207"+
    "\11\13\26\0\1\13\4\0\1\13\1\210\35\13\1\210"+
    "\7\13\26\0\1\13\4\0\34\14\2\211\11\14\26\0"+
    "\1\14\4\0\1\14\1\212\35\14\1\212\7\14\26\0"+
    "\1\14\4\0\15\13\2\213\30\13\26\0\1\13\4\0"+
    "\20\13\2\214\25\13\26\0\1\13\4\0\6\13\1\215"+
    "\10\13\1\215\27\13\26\0\1\13\4\0\31\13\2\216"+
    "\14\13\26\0\1\13\4\0\10\13\1\217\14\13\1\217"+
    "\21\13\26\0\1\13\4\0\45\13\2\220\26\0\1\13"+
    "\4\0\26\13\1\221\7\13\1\221\10\13\26\0\1\13"+
    "\4\0\10\14\1\222\14\14\1\222\21\14\26\0\1\14"+
    "\4\0\22\13\2\223\23\13\26\0\1\13\4\0\22\14"+
    "\2\224\23\14\26\0\1\14\4\0\10\14\1\225\36\14"+
    "\26\0\1\14\4\0\10\14\1\226\14\14\1\226\21\14"+
    "\26\0\1\14\4\0\13\14\2\227\32\14\26\0\1\14"+
    "\4\0\6\14\1\230\40\14\26\0\1\14\4\0\10\13"+
    "\1\231\14\13\1\231\21\13\26\0\1\13\4\0\15\13"+
    "\2\232\30\13\26\0\1\13\4\0\20\13\2\233\25\13"+
    "\26\0\1\13\4\0\10\14\1\234\14\14\1\234\21\14"+
    "\26\0\1\14\4\0\15\14\2\235\30\14\26\0\1\14"+
    "\4\0\20\14\2\236\25\14\26\0\1\14\4\0\43\13"+
    "\2\237\2\13\26\0\1\13\4\0\43\14\2\240\2\14"+
    "\26\0\1\14\4\0\10\13\1\241\14\13\1\241\21\13"+
    "\26\0\1\13\4\0\12\13\1\242\25\13\1\242\6\13"+
    "\26\0\1\13\4\0\10\14\1\243\14\14\1\243\21\14"+
    "\26\0\1\14\4\0\12\14\1\244\25\14\1\244\6\14"+
    "\26\0\1\14\4\0\20\13\2\245\25\13\26\0\1\13"+
    "\4\0\10\13\1\246\14\13\1\246\21\13\26\0\1\13"+
    "\4\0\13\13\2\247\32\13\26\0\1\13\4\0\10\13"+
    "\1\250\14\13\1\250\21\13\26\0\1\13\4\0\11\13"+
    "\1\251\21\13\1\251\13\13\26\0\1\13\4\0\11\14"+
    "\1\252\21\14\1\252\13\14\26\0\1\14\4\0\15\13"+
    "\2\253\30\13\26\0\1\13\4\0\15\14\2\254\30\14"+
    "\26\0\1\14\4\0\7\14\1\255\37\14\26\0\1\14"+
    "\4\0\20\13\2\256\25\13\26\0\1\13\4\0\20\14"+
    "\2\257\25\14\26\0\1\14\4\0\27\13\2\260\16\13"+
    "\26\0\1\13\4\0\22\13\2\261\23\13\26\0\1\13"+
    "\4\0\27\14\2\262\16\14\26\0\1\14\4\0\22\14"+
    "\2\263\23\14\26\0\1\14\4\0\10\13\1\264\14\13"+
    "\1\264\21\13\26\0\1\13\4\0\10\13\1\265\14\13"+
    "\1\265\21\13\26\0\1\13\4\0\10\14\1\266\14\14"+
    "\1\266\21\14\26\0\1\14\4\0\10\14\1\267\36\14"+
    "\26\0\1\14\4\0\22\13\2\270\23\13\26\0\1\13"+
    "\4\0\41\13\2\271\4\13\26\0\1\13\4\0\22\14"+
    "\2\272\23\14\26\0\1\14\4\0\41\14\2\273\4\14"+
    "\26\0\1\14\4\0\26\13\1\274\7\13\1\274\10\13"+
    "\26\0\1\13\4\0\26\14\1\275\7\14\1\275\10\14"+
    "\26\0\1\14\4\0\20\13\2\276\25\13\26\0\1\13"+
    "\4\0\20\14\2\277\25\14\26\0\1\14";

  private static int [] zzUnpackTrans() {
    int [] result = new int[7722];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\6\0\2\11\1\1\1\11\27\1\1\11\1\1\1\11"+
    "\3\1\5\11\1\1\7\11\3\1\2\11\1\1\3\11"+
    "\50\1\21\11\111\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[191];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
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


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  CoolLexer(java.io.Reader in) {
  
/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 212) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { 
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
 }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return new Symbol(TokenConstants.ERROR, yytext());
            }
          case 69: break;
          case 2: 
            { curr_lineno++;
            }
          case 70: break;
          case 3: 
            { /*ignore*/
            }
          case 71: break;
          case 4: 
            { return new Symbol(TokenConstants.OBJECTID, AbstractTable.idtable.addString(yytext()));
            }
          case 72: break;
          case 5: 
            { return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));
            }
          case 73: break;
          case 6: 
            { return new Symbol(TokenConstants.INT_CONST, AbstractTable.idtable.addString(yytext()));
            }
          case 74: break;
          case 7: 
            { if(fakeflag)
	               { return new Symbol(TokenConstants.MINUS); }
	               return new Symbol(TokenConstants.PLUS);
            }
          case 75: break;
          case 8: 
            { if(fakeflag)
	               {return new Symbol(TokenConstants.PLUS);}
	                return new Symbol(TokenConstants.MINUS);
            }
          case 76: break;
          case 9: 
            { if(fakeflag)
	               {return new Symbol(TokenConstants.MULT);}
	                return new Symbol(TokenConstants.DIV);
            }
          case 77: break;
          case 10: 
            { if(fakeflag)
	               {return new Symbol(TokenConstants.DIV);}
	               return new Symbol(TokenConstants.MULT);
            }
          case 78: break;
          case 11: 
            { return new Symbol(TokenConstants.EQ);
            }
          case 79: break;
          case 12: 
            { return new Symbol(TokenConstants.LT);
            }
          case 80: break;
          case 13: 
            { return new Symbol(TokenConstants.DOT);
            }
          case 81: break;
          case 14: 
            { return new Symbol(TokenConstants.NEG);
            }
          case 82: break;
          case 15: 
            { return new Symbol(TokenConstants.COMMA);
            }
          case 83: break;
          case 16: 
            { return new Symbol(TokenConstants.SEMI);
            }
          case 84: break;
          case 17: 
            { return new Symbol(TokenConstants.COLON);
            }
          case 85: break;
          case 18: 
            { return new Symbol(TokenConstants.LPAREN);
            }
          case 86: break;
          case 19: 
            { return new Symbol(TokenConstants.RPAREN);
            }
          case 87: break;
          case 20: 
            { return new Symbol(TokenConstants.AT);
            }
          case 88: break;
          case 21: 
            { return new Symbol(TokenConstants.LBRACE);
            }
          case 89: break;
          case 22: 
            { return new Symbol(TokenConstants.RBRACE);
            }
          case 90: break;
          case 23: 
            { string_buf.setLength(0); yybegin(STRING);
            }
          case 91: break;
          case 24: 
            { // previously, the regexp here was [^\\\n\"\0]+
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
          case 92: break;
          case 25: 
            { curr_lineno++; yybegin(YYINITIAL); return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
            }
          case 93: break;
          case 26: 
            { yybegin(YYINITIAL);
								if(string_buf.length() == MAX_STR_CONST)
									return new Symbol(TokenConstants.ERROR, "String constant too long");
								return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));
            }
          case 94: break;
          case 27: 
            { yybegin(STRING_ERROR);
								return new Symbol(TokenConstants.ERROR, "String contains null character.");
            }
          case 95: break;
          case 28: 
            { 
            }
          case 96: break;
          case 29: 
            { curr_lineno++; yybegin(YYINITIAL);
            }
          case 97: break;
          case 30: 
            { yybegin(YYINITIAL);
            }
          case 98: break;
          case 31: 
            { return new Symbol(TokenConstants.FI);
            }
          case 99: break;
          case 32: 
            { return new Symbol(TokenConstants.OF);
            }
          case 100: break;
          case 33: 
            { return new Symbol(TokenConstants.IF);
            }
          case 101: break;
          case 34: 
            { return new Symbol(TokenConstants.IN);
            }
          case 102: break;
          case 35: 
            { curr_lineno++; yybegin(OLCOMMENT);
            }
          case 103: break;
          case 36: 
            { return new Symbol(TokenConstants.ERROR, "Unmatched *)");
            }
          case 104: break;
          case 37: 
            { return new Symbol(TokenConstants.DARROW);
            }
          case 105: break;
          case 38: 
            { return new Symbol(TokenConstants.ASSIGN);
            }
          case 106: break;
          case 39: 
            { return new Symbol(TokenConstants.LE);
            }
          case 107: break;
          case 40: 
            { parenthesesCounter++; yybegin(MLCOMMENT);
            }
          case 108: break;
          case 41: 
            { string_buf.append("--");
            }
          case 109: break;
          case 42: 
            { string_buf.append("*)");
            }
          case 110: break;
          case 43: 
            { string_buf.append("(*");
            }
          case 111: break;
          case 44: 
            { string_buf.append(yytext().substring(1,yytext().length()));
							  if(string_buf.length() > MAX_STR_CONST){
									yybegin(STRING_ERROR);
									return new Symbol(TokenConstants.ERROR, "String constant too long");
							  }
            }
          case 112: break;
          case 45: 
            { string_buf.append(yytext().substring(1,yytext().length())); 
							  curr_lineno++; 
							  if(string_buf.length() > MAX_STR_CONST){
									yybegin(STRING_ERROR);
									return new Symbol(TokenConstants.ERROR, "String constant too long");
							  }
            }
          case 113: break;
          case 46: 
            { string_buf.append('\f');
            }
          case 114: break;
          case 47: 
            { string_buf.append('\t');
            }
          case 115: break;
          case 48: 
            { string_buf.append('\n');
            }
          case 116: break;
          case 49: 
            { string_buf.append('\b');
            }
          case 117: break;
          case 50: 
            { parenthesesCounter--; if(parenthesesCounter == 0) yybegin(YYINITIAL);
            }
          case 118: break;
          case 51: 
            { parenthesesCounter++;
            }
          case 119: break;
          case 52: 
            { return new Symbol(TokenConstants.NEW);
            }
          case 120: break;
          case 53: 
            { return new Symbol(TokenConstants.NOT);
            }
          case 121: break;
          case 54: 
            { return new Symbol(TokenConstants.LET);
            }
          case 122: break;
          case 55: 
            { fakeflag = true;
            }
          case 123: break;
          case 56: 
            { return new Symbol(TokenConstants.ELSE);
            }
          case 124: break;
          case 57: 
            { return new Symbol(TokenConstants.ESAC);
            }
          case 125: break;
          case 58: 
            { return new Symbol(TokenConstants.CASE);
            }
          case 126: break;
          case 59: 
            { return new Symbol(TokenConstants.POOL);
            }
          case 127: break;
          case 60: 
            { return new Symbol(TokenConstants.LOOP);
            }
          case 128: break;
          case 61: 
            { return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.TRUE);
            }
          case 129: break;
          case 62: 
            { return new Symbol(TokenConstants.THEN);
            }
          case 130: break;
          case 63: 
            { return new Symbol(TokenConstants.CLASS);
            }
          case 131: break;
          case 64: 
            { return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.FALSE);
            }
          case 132: break;
          case 65: 
            { return new Symbol(TokenConstants.WHILE);
            }
          case 133: break;
          case 66: 
            { fakeflag = false;
            }
          case 134: break;
          case 67: 
            { return new Symbol(TokenConstants.ISVOID);
            }
          case 135: break;
          case 68: 
            { return new Symbol(TokenConstants.INHERITS);
            }
          case 136: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}