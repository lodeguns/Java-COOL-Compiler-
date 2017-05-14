import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java_cup.runtime.Symbol;

/** Static semantics driver class */
class Semant2 {

	/** Reads AST from from console, and outputs the new AST */
	public static void main(String[] args) {
		args = Flags.handleFlags(args);
		try {
			if(args.length == 1) {
				FileInputStream file = new FileInputStream(args[0]);
				ASTLexer lexer = new ASTLexer(new InputStreamReader(file));
				//ASTLexer lexer = new ASTLexer(System.in);
				AstCup parser = new AstCup(lexer);
				Object result = parser.parse().value;
				((Program)result).semant();
				((Program)result).dump_with_types(System.out, 0);
			} else {
				System.out.println("USAGE: java -jar seman.jar filename\n");
				System.exit(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
