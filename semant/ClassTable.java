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

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
	private int semantErrors;
	private PrintStream errorStream;
	private dag<AbstractSymbol> dag = null;
	private Vector<class_c> writtenAfterClasses = null;
	private Classes _cls = null;
	private class_c Object_class = null, Int_class = null, IO_class = null, Bool_class = null, Str_class = null;

	/** Creates data structures representing basic Cool classes (Object,
	 * IO, Int, Bool, String).  Please note: as is this method does not
	 * do anything useful; you will need to edit it to make if do what
	 * you want.
	 * */
	private void installBasicClasses(Classes cls) {
		AbstractSymbol filename 
		= AbstractTable.stringtable.addString("<basic class>");

		// The following demonstrates how to create dummy parse trees to
		// refer to basic Cool classes.  There's no need for method
		// bodies -- these are already built into the runtime system.

		// IMPORTANT: The results of the following expressions are
		// stored in local variables.  You will want to do something
		// with those variables at the end of this method to make this
		// code meaningful.

		// The Object class has no parent class. Its methods are
		//        cool_abort() : Object    aborts the program
		//        type_name() : Str        returns a string representation 
		//                                 of class name
		//        copy() : SELF_TYPE       returns a copy of the object

		Object_class = 
				new class_c(0, 
						TreeConstants.Object_, 
						TreeConstants.No_class,
						new Features(0)
				.appendElement(new method(0, 
						TreeConstants.cool_abort, 
						new Formals(0), 
						TreeConstants.Object_, 
						new no_expr(0)))
						.appendElement(new method(0,
								TreeConstants.type_name,
								new Formals(0),
								TreeConstants.Str,
								new no_expr(0)))
								.appendElement(new method(0,
										TreeConstants.copy,
										new Formals(0),
										TreeConstants.SELF_TYPE,
										new no_expr(0))),
										filename);

		// The IO class inherits from Object. Its methods are
		//        out_string(Str) : SELF_TYPE  writes a string to the output
		//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
		//        in_string() : Str            reads a string from the input
		//        in_int() : Int                "   an int     "  "     "

		IO_class = 
				new class_c(0,
						TreeConstants.IO,
						TreeConstants.Object_,
						new Features(0)
				.appendElement(new method(0,
						TreeConstants.out_string,
						new Formals(0)
				.appendElement(new formalc(0,
						TreeConstants.arg,
						TreeConstants.Str)),
						TreeConstants.SELF_TYPE,
						new no_expr(0)))
						.appendElement(new method(0,
								TreeConstants.out_int,
								new Formals(0)
						.appendElement(new formalc(0,
								TreeConstants.arg,
								TreeConstants.Int)),
								TreeConstants.SELF_TYPE,
								new no_expr(0)))
								.appendElement(new method(0,
										TreeConstants.in_string,
										new Formals(0),
										TreeConstants.Str,
										new no_expr(0)))
										.appendElement(new method(0,
												TreeConstants.in_int,
												new Formals(0),
												TreeConstants.Int,
												new no_expr(0))),
												filename);

		// The Int class has no methods and only a single attribute, the
		// "val" for the integer.

		Int_class = 
				new class_c(0,
						TreeConstants.Int,
						TreeConstants.Object_,
						new Features(0)
				.appendElement(new attr(0,
						TreeConstants.val,
						TreeConstants.prim_slot,
						new no_expr(0))),
						filename);

		// Bool also has only the "val" slot.
		Bool_class = 
				new class_c(0,
						TreeConstants.Bool,
						TreeConstants.Object_,
						new Features(0)
				.appendElement(new attr(0,
						TreeConstants.val,
						TreeConstants.prim_slot,
						new no_expr(0))),
						filename);

		// The class Str has a number of slots and operations:
		//       val                              the length of the string
		//       str_field                        the string itself
		//       length() : Int                   returns length of the string
		//       concat(arg: Str) : Str           performs string concatenation
		//       substr(arg: Int, arg2: Int): Str substring selection

		Str_class =
				new class_c(0,
						TreeConstants.Str,
						TreeConstants.Object_,
						new Features(0)
				.appendElement(new attr(0,
						TreeConstants.val,
						TreeConstants.Int,
						new no_expr(0)))
						.appendElement(new attr(0,
								TreeConstants.str_field,
								TreeConstants.prim_slot,
								new no_expr(0)))
								.appendElement(new method(0,
										TreeConstants.length,
										new Formals(0),
										TreeConstants.Int,
										new no_expr(0)))
										.appendElement(new method(0,
												TreeConstants.concat,
												new Formals(0)
										.appendElement(new formalc(0,
												TreeConstants.arg, 
												TreeConstants.Str)),
												TreeConstants.Str,
												new no_expr(0)))
												.appendElement(new method(0,
														TreeConstants.substr,
														new Formals(0)
												.appendElement(new formalc(0,
														TreeConstants.arg,
														TreeConstants.Int))
														.appendElement(new formalc(0,
																TreeConstants.arg2,
																TreeConstants.Int)),
																TreeConstants.Str,
																new no_expr(0))),
																filename);

		/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */

		dag = new dag<AbstractSymbol>();
        /* Il metodo installBasicClasses della classe ClassTable si occupa di creare una
         * nuova istanza della classe dag e, una volta creata, inserisce in essa le cinque
         * classi base (Object, IO, Int, String e Bool).
         * Dopodichè il metodo scorre la lista delle classe e inserisce nel dag le classi che incontra. */

		dag.add(Object_class.name,IO_class.name);
		dag.add(Object_class.name,Str_class.name);
		dag.add(Object_class.name,Bool_class.name);
		dag.add(Object_class.name,Int_class.name);

		class_c current = null;
		Feature cfeature = null;
		writtenAfterClasses = new Vector<class_c>();
		boolean errors = false, mainexist = false;

		for (@SuppressWarnings("rawtypes")
		Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
			current = (class_c)e.nextElement();
			if( current.getName().equals( TreeConstants.Bool) 
					|| current.getName().equals( TreeConstants.Str )
					|| current.getName().equals( TreeConstants.Int ) 
					|| current.getName().equals( TreeConstants.SELF_TYPE) 
					|| current.getName().equals( TreeConstants.IO ) 
					|| current.getName().equals( TreeConstants.Object_ ))
			{ semantError(current).println("Redefinition of basic class " + current.getName() + ".");
				//majorPriorityErrorFound = true;
				errors = true;
			}

			if( current.getParent().equals( TreeConstants.Bool ) 
					|| current.getParent().equals( TreeConstants.Str )
					|| current.getParent().equals( TreeConstants.Int ) 
					|| current.getParent().equals( TreeConstants.SELF_TYPE) )
			{ semantError(current).println("A class can not inherits class " + current.getParent() + ".");
				//majorPriorityErrorFound = true;
				errors = true;
			}

			if ( current.getParent().equals(current.name) ){
				semantError(current).println("Class " + current.getName() +
						" or an ancestor of " + current.getName() + ", is involved in an inheritence cicle.");
				errors = true;
			}
			
		    /**
		     * Add an edge to the graph; if either vertex does not exist, it's added.
		     * Se quindi già esiste un arco, significa che già esistono 2 nodi, quindi sto duplicando la classe.
		     * Può essere che genero cicli ecco la ragione del secondo if.
		     * Se supera anche questo test è possibile aggiungere current a writtenAfterClasses
		     */
			
			if( dag.contains(current.getParent()) ){
				if( !dag.add(current.getParent(), current.name) ){
					semantError(current).println("Duplicated Class "+current.getName()+".");
					errors = true;
				}
				if ( !dag.isDag() ){
					semantError(current).println("Class " + current.getName() + " or an ancestor of "+
				                                  current.getName() + 
				                                  ", is involved in an inheritence cicle.");
					errors = true;
				}
			}else{
				//l'ordine in cui sono scritte le classi potrebbe non essere quello lineare.
				writtenAfterClasses.add(current);
			}
		}
	    /**
	     * Esegui un controllo sul Main se ha la feature main()
	     */

		if( dag.contains(TreeConstants.Main) ){
			for (@SuppressWarnings("rawtypes")
			Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
				current = (class_c)e.nextElement();
				if( current.name.equals(TreeConstants.Main) ){
					for (@SuppressWarnings("rawtypes")
					Enumeration e2 = current.getFeatures().getElements(); e2.hasMoreElements(); ) {
						cfeature = (Feature)e2.nextElement();
						if( cfeature instanceof method ){
							if ( ((method)cfeature).name.equals(TreeConstants.main_meth) )
								mainexist = true;
						}
					}				
				}
			}
		}

		class_c p = null;
		if( writtenAfterClasses.size() > 0 ){
			for ( int i = 0; i < writtenAfterClasses.size(); i++ ){
				class_c curr = writtenAfterClasses.get(i);
				for (@SuppressWarnings("rawtypes")
				Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
					current = (class_c)e.nextElement();
					if( current.name.equals(curr.getParent()) ){
						p = current;
						break;
					}
				}
				if( writtenAfterClasses.contains(p) ){
					System.out.println("Errore parte qui::: " + p.name.toString());
					System.out.println("Errore parte qui::: " + current.name.toString());
					semantError(current).println("Class "+current.getName()+" or an ancestor of "+ current.getName() + ", is involved in an inheritence cicle. 1");
					errors = true;
				}else if( dag.contains(curr.getParent()) ){
					if( !dag.add(curr.getParent(), curr.name) ){
						semantError(curr).println("Duplicated Class "+curr.getName()+".");
						errors = true;
					}
					if ( !dag.isDag() ){
						semantError(current).println("Class "+current.getName()+" or an ancestor of "+ current.getName() + ", is involved in an inheritence cicle. 2");
						errors = true;
					}
				}else{
					semantError(curr).println("Class "+curr.getName()+" inherits from an undefined class "+ curr.getParent()+".");
					errors = true;
				} 
			}
		}
		
		if( !mainexist ){
			semantError(current).println("Class Main or method main does not exist!");
			errors = true;
		}

		if ( !errors ){
			System.out.println("Dag:\n"+dag);
			System.out.println("Top-sorted Dag:\n"+dag.topSort().toString());
			Checker checker = new Checker( this, cls, dag.topSort() );
			checker.explore(); //type check ---> Checker
		}

	}



	public ClassTable(Classes cls) {
		semantErrors = 0;
		errorStream = System.err;

		/* fill this in */

		_cls = cls;

		installBasicClasses(cls);

	}

	
	public AbstractSymbol getUncle(AbstractSymbol d ){

		AbstractSymbol uncle = null;
		
		AbstractSymbol parent = dag.getParent(d);
		
		
		if(parent!= null){
			uncle = dag.getParent(parent);
		}
		
		
		return uncle;

	}
	
	public AbstractSymbol getFather(AbstractSymbol d){
		
		AbstractSymbol parent = dag.getParent(d);
		return parent;

		
	}
	
	
	
	public boolean isAncestor( AbstractSymbol p, AbstractSymbol d ){

		if ( p.equals(d) )
			return true;

		if( d.equals(TreeConstants.Object_) )
			return false;

		AbstractSymbol parent = dag.getParent(d);

		return isAncestor(p, parent);

	}

	public List<AbstractSymbol> ancestorList( AbstractSymbol as ){

		if ( !dag.contains(as) )
			return null;

		List<AbstractSymbol> toReturn = new LinkedList<AbstractSymbol>();
		toReturn.add(as);
		AbstractSymbol p = dag.getParent(as);

		if ( p != null ){
			while(!p.equals(TreeConstants.Object_)){
				toReturn.add(p);
				p = dag.getParent(p);
			}
			toReturn.add(TreeConstants.Object_);
		}else toReturn.add(TreeConstants.Object_);


		return toReturn;

	}

	public List<class_c> ancestorListwClasses( AbstractSymbol as ){

		if ( !dag.contains(as) )
			return null;

		List<class_c> toReturn = new LinkedList<class_c>();
		class_c current = null;
		for (@SuppressWarnings("rawtypes")
		Enumeration e = _cls.getElements(); e.hasMoreElements(); ) {
			current = (class_c)e.nextElement();
			if( current.name.equals(as) )
				toReturn.add(current);
		}

		AbstractSymbol p = dag.getParent(as);
		current = null;

		if ( p != null ){
			if ( p.equals(TreeConstants.IO) ){
				toReturn.add(IO_class);
				toReturn.add( Object_class );
			}else{
				while(!p.equals(TreeConstants.Object_)){
					for (@SuppressWarnings("rawtypes")
					Enumeration e = _cls.getElements(); e.hasMoreElements(); ) {
						current = (class_c)e.nextElement();
						if( current.name.equals(p) )
							toReturn.add(current);
					}
					p = dag.getParent(p);
					if ( p.equals(TreeConstants.IO) ){
						toReturn.add(IO_class);
					}
				}
				toReturn.add( Object_class );
			}
		}else toReturn.add(null);


		return toReturn;

	}



	public AbstractSymbol nearestCommonAncestor( AbstractSymbol s1, AbstractSymbol s2 ){

		if ( s1.equals(TreeConstants.SELF_TYPE) && s2.equals(TreeConstants.SELF_TYPE) )
			return TreeConstants.SELF_TYPE;

		List<AbstractSymbol> s1_list = ancestorList(s1);
		List<AbstractSymbol> s2_list = ancestorList(s2);

		if ( s1_list.size() <= s2_list.size() ){
			for ( int i = 0; i < s1_list.size(); i++ ){
				if( s2_list.contains( s1_list.get(i) ) )
					return (AbstractSymbol) s1_list.get(i);
			}
		}else{
			for ( int i = 0; i < s2_list.size(); i++ ){
				if( s1_list.contains( s2_list.get(i) ) )
					return (AbstractSymbol) s2_list.get(i);
			}
		}

		return null;
	}

	public class_c getBasicClass( AbstractSymbol whatClass ){

		if( whatClass.equals(TreeConstants.Bool ) )
			return Bool_class;
		else if(whatClass.equals(TreeConstants.Str ) )
			return Str_class;
		else if(whatClass.equals(TreeConstants.Int) )
			return Int_class;
		else if(whatClass.equals(TreeConstants.IO) )
			return IO_class;

		return null;

	}

	/** Prints line number and file name of the given class.
	 *
	 * Also increments semantic error count.
	 *
	 * @param c the class
	 * @return a print stream to which the rest of the error message is
	 * to be printed.
	 *
	 * */
	public PrintStream semantError(class_c c) {
		return semantError(c.getFilename(), c);
	}

	/** Prints the file name and the line number of the given tree node.
	 *
	 * Also increments semantic error count.
	 *
	 * @param filename the file name
	 * @param t the tree node
	 * @return a print stream to which the rest of the error message is
	 * to be printed.
	 *
	 * */
	public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
		errorStream.print(filename + ":" + t.getLineNumber() + ": ");
		return semantError();
	}

	/** Increments semantic error count and returns the print stream for
	 * error messages.
	 *
	 * @return a print stream to which the error message is
	 * to be printed.
	 *
	 * */
	public PrintStream semantError() {
		semantErrors++;
		return errorStream;
	}

	/** Returns true if there are any static semantic errors. */
	public boolean errors() {
		return semantErrors != 0;
	}


}


