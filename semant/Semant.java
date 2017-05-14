/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;

/** Static semantics driver class */
class Semant {

    /** Reads AST from from consosle, and outputs the new AST */
    public static void main(String[] args) {
	args = Flags.handleFlags(args);
	FileReader inputFile = null;
	try {
		
		delete(args[0],1,1);
		
		
		inputFile = new FileReader(args[0]);
		//BufferedReader reader = new BufferedReader(inputFile);
		//reader.readLine(); //la prima linea poi da errore
		
		
	    ASTLexer lexer = new ASTLexer(inputFile);
	    AstCup parser = new AstCup(lexer);
	    PrintStream ps = new PrintStream(System.getProperty("user.dir")+"/src/"+"out.txt");
	    PrintStream tps = new PrintStream(System.getProperty("user.dir")+"/src/"+"tree.txt");
	    System.setOut(ps);
	    Object result = parser.parse().value;
	    ((Program)result).semant();
	    ps.close();
	    System.setOut(tps);
	    ((Program)result).dump_with_types(System.out, 0);
	    tps.close();
	    
	} catch (Exception ex) {
	    ex.printStackTrace(System.err);
	}
    }
    
    
	static void delete(String filename, int startline, int numlines)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(filename));
 
			//String buffer to store contents of the file
			StringBuffer sb=new StringBuffer("");
 
			//Keep track of the line number
			int linenumber=1;
			String line;
 
			while((line=br.readLine())!=null)
			{
				//Store each valid line in the string buffer
				if(linenumber<startline||linenumber>=startline+numlines)
					sb.append(line+"\n");
				linenumber++;
			}
			if(startline+numlines>linenumber)
				System.out.println("End of file reached.");
			br.close();
 
			FileWriter fw=new FileWriter(new File(filename));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
	}
}






