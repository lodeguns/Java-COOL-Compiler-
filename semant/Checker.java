import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Checker {

	Classes cls = null;
	List<AbstractSymbol> sorted = null;
	ClassTable cTable = null;
	List<SymbolTable> globalSymbolTable = null;
	public  Integer globalcount = 0;
	List<String> m    = new LinkedList<String>();
	List<String> maft = new LinkedList<String>();


	public final static boolean DEBUGMODE=true;
	
	
	public Checker( ClassTable cTable, Classes cls, List<AbstractSymbol> topSort ){
		this.cls = cls;
		sorted = topSort;
		this.cTable = cTable;
		globalSymbolTable = new LinkedList<SymbolTable>();
		
	}

	public void explore(){
		class_c current = null;
		class_c next = null;

		for ( int i = 0; i < sorted.size(); i++ ){
			if( !sorted.get(i).equals(TreeConstants.Object_) && 
				!sorted.get(i).equals(TreeConstants.Bool)    && 
				!sorted.get(i).equals(TreeConstants.IO)      && 
				!sorted.get(i).equals(TreeConstants.Int)     && 
				!sorted.get(i).equals(TreeConstants.Str)){
				
				for (@SuppressWarnings("rawtypes")
				Enumeration e = cls.getElements(); e.hasMoreElements(); ){
					next = (class_c)e.nextElement();
					if ( next.getName().equals(sorted.get(i)) ){
						current = next;
						break;
					}
				}
				     	current.getSymbolTable().addId(TreeConstants.self, TreeConstants.SELF_TYPE);
				   	 
						current.getSymbolTable().addId(TreeConstants.SELF_TYPE, current.name);
						visit(current, current.getFeatures());
						
     	   } //if
		} //for

	}

	
	private void visit(class_c current, Features f) {
		Enumeration<?> features = f.getElements();
		
		LinkedList<attr> at1 = new LinkedList<attr>();
		LinkedList<method> meth1 = new LinkedList<method>();
		
		while( features.hasMoreElements() ){
			Feature _f = (Feature)features.nextElement();
			if ( _f instanceof attr )
			{   
				//at1.add((attr)_f);
				AbstractSymbol att = ((attr) _f).name;
				System.out.println("attr visitato ::: " + att.str);
				visit( current, (attr)_f);
			} else if ( _f instanceof method )
			{
				//meth1.add((method) _f);
				//debug("Method List", (method) meth1.get(j));
				addMethodList(current, (method) _f, "Feat method");
				visit( current, (method) _f);
				current.getSymbolTable().addId(((method) _f).name, (method) _f);
		
			}
		}
	    
		//for(int i =0; i<at1.size(); i++)
		//{   
		//	visit( current, (attr)at1.get(i));
		//}
		
		/*for(int j =0; j<meth1.size(); j++)
		{  
			//debug("Method List", (method) meth1.get(j));
			addMethodList(current, (method)meth1.get(j), "Feat method");
			visit( current, (method)meth1.get(j));
			current.getSymbolTable().addId(((method)meth1.get(j)).name, (method)meth1.get(j));
		}*/


	}

	
	private void visit(class_c current, attr feature) {


        
		Object fromST = current.getSymbolTable().lookup(feature.name);

		if ( fromST != null ){
			if(feature.name.equals(TreeConstants.self)){
				cTable.semantError(current).println(feature.lineNumber + ": 'self' cannot be the name of attribute.");
				//semantError().println(a.lineNumber + ": 'self' cannot be the name of attribute.");
				return;
			}
			else //attributo gia dichiarato
				cTable.semantError(current).println(feature.lineNumber + "attribute " + feature.name + "is multiply defined.");
			    //semantError().println(a.lineNumber + "attribute " + a.name + "is multiply defined.");
		}else{

			class_c tmp = current;
			List<class_c> ancestors = cTable.ancestorListwClasses(tmp.name);		
			
			for ( int i = 0; i < ancestors.size(); i++ ){
				if( isInherited( ancestors.get(i), feature ) ){
					cTable.semantError(current).println(feature.lineNumber + ": Attribute " + feature.name + " is an attribute of an inherited class.");//semantError().println(a.lineNumber + ": Attribute " + a.name + " is an attribute of an inherited class.");
					return;
				}
			}
			//aggiungo l'attributo alla tabella dei simboli della classe
			AbstractSymbol t = visit(current, feature.init);
			if(!t.equals(TreeConstants.No_type))
				if(!cTable.isAncestor(feature.type_decl, t))
					cTable.semantError().println(feature.lineNumber + ": type mismatch : " + feature.type_decl + " <= " + t);

			current.getSymbolTable().addId(feature.name, feature);
			//
	      

		}

	}

	private void visit(class_c current, method feature) {

		method founded = null;
		class_c tmp = current;
		List<class_c> ancestors = cTable.ancestorListwClasses(tmp.name);
        

		for ( int i = 0; i < ancestors.size(); i++ ){
			if( (founded = isInherited( ancestors.get(i), feature ) ) != null ){
    			break;
			}
		}

				
		if( founded != null ){
			//metodo ereditato, quindi posso sovrascriverlo ma non sovraccaricarlo

			//controllo tipo di ritorno
			if( !(founded.return_type.equals(feature.return_type)) ){
				cTable.semantError(current.filename,feature).println("In redefined method " + feature.name + ", return type " + feature.return_type + " is different from original return type " + founded.return_type);
				return;
			}else{ //controllo il numero di formal poi il tipo
				Formals methodsFormals = feature.formals;
				Formals in_mf = founded.formals;
				Enumeration<?> m_formals = methodsFormals.getElements();
				Enumeration<?> in_mf_formals=in_mf.getElements();

				boolean flag_type=true;
				formalc f1, f2, err_f1 = null, err_f2 = null;
				boolean flag_mf = m_formals.hasMoreElements();
				boolean flag_in_mf = in_mf_formals.hasMoreElements();

				if(flag_mf && flag_in_mf){
					do{
						f1=(formalc)m_formals.nextElement();
						f2=(formalc)in_mf_formals.nextElement();


						if(!(f1.type_decl.equals(f2.type_decl))){
							// tipo dei parametri non esatto
							err_f1=f1;
							err_f2=f2;
							flag_type=false;
						}

						flag_mf=m_formals.hasMoreElements();
						flag_in_mf=in_mf_formals.hasMoreElements();
					}while(flag_mf && flag_in_mf);
				}

				if(flag_mf != flag_in_mf){//uno dei due aveva ancora parametri formali
					cTable.semantError(current.filename,feature).println("Incompatible number of formal parameters in redefined method " + feature.name + ".");
					return;
				}
				if(!flag_type){ //incompatibilita di tipo dei parametri
					cTable.semantError(current.filename, feature).println("In redefined method " + feature.name + ", parameter type " + err_f1.type_decl + " is different from original type " + err_f2.type_decl + ".");
					return;
				}

				Enumeration<?> formalParamethers = feature.formals.getElements();

				while(formalParamethers.hasMoreElements()){
					formalc f = (formalc)formalParamethers.nextElement();
					feature.getSymbolTable().addId(f.name, f);
	
				}

				globalSymbolTable.add( feature.getSymbolTable() );
				
		        debug("override:: ", feature);
		

				AbstractSymbol body_return_type = visit( current, feature.expr );

				if( body_return_type.equals(TreeConstants.SELF_TYPE) )
					body_return_type = feature.return_type;

				if( !feature.return_type.equals(body_return_type) ){
					if( !cTable.isAncestor(feature.return_type, body_return_type ) )
						cTable.semantError(current.filename, feature).println("In redefined method " + feature.name + ", return type " + feature.return_type + " is not an ancestor of " + body_return_type + ".");
				}

				feature.getSymbolTable().exitScope();
                
				globalSymbolTable.remove( globalSymbolTable.size()-1 );

				return;
			}                                       
		}
		else{ //nuovo metodo da aggiungere alla tabella dei simboli


			Enumeration<?> formalParamethers = feature.formals.getElements();

			while(formalParamethers.hasMoreElements()){
				formalc f = (formalc)formalParamethers.nextElement();

				//alcuni controlli su formal
				if(f.type_decl.equals(TreeConstants.SELF_TYPE)){
					cTable.semantError(current.filename, feature).println("Formal parameter "+ f.name + "cannot have type SELF_TYPE");
					return;
				}
				if(f.name.equals(TreeConstants.self)){
					cTable.semantError(current.filename, feature).println("self cannot be the name of a formal parameter");
					return;
				}else if(feature.getSymbolTable().probe(f.name) != null){
					cTable.semantError(current.filename, feature).println("Formal parameter "+f.name+" is multiply defined.");
					return;
				}else{
					feature.getSymbolTable().addId(f.name, f);
				}
			}
			globalSymbolTable.add( feature.getSymbolTable() );

	        //debug("normal:: ", feature);
			AbstractSymbol inferred_type = (AbstractSymbol)visit(current, feature.expr);

			feature.getSymbolTable().exitScope();

			globalSymbolTable.remove( globalSymbolTable.size()-1 );

			AbstractSymbol return_type = feature.return_type;

			/*
			 * Se i due sono uguali esco direttamente
			 */

			if(feature.return_type.equals(inferred_type))
				return;

			/*
			 * Se sono diversi, devo considerare il caso in cui uno dei due sia SELF_TYPE
			 */

			if(return_type.equals(TreeConstants.SELF_TYPE) || inferred_type.equals(TreeConstants.SELF_TYPE)){
				// se uno dei due lo e in ogni caso devo recuperare il "valore attuale" di self_type
				AbstractSymbol mySelf = current.name;

				// a seconda se uno o entrambi sono SELF_TYPE, li aggiorno
				if(return_type.equals(TreeConstants.SELF_TYPE))
					return_type = mySelf;

				if(inferred_type.equals(TreeConstants.SELF_TYPE))
					inferred_type = mySelf;
			}

			// do errore se inferred_type non e compatibile con return_type
			if(!cTable.isAncestor(return_type, inferred_type )){
				cTable.semantError(current.filename, feature).println("Inferred return type " + inferred_type + " of method " + feature.name + " does not conform to declared return type " + feature.return_type);
				return;
			}
			System.out.println(feature.getSymbolTable().toString());
			// restituisco m.return_type perche sicuramente non e stato modificato
			// (se era SELF_TYPE e rimasto SELF_TYPE
			return ;

		}
		
	}

	private AbstractSymbol visit(class_c current, Expression e) {
		AbstractSymbol expr_type = TreeConstants.No_type;
		if(e instanceof assign)
            expr_type = (AbstractSymbol)visit(current, (assign)e);  //OK
		else if(e instanceof static_dispatch){
			//debug("Static dispatch\n", (dispatch)e);
			expr_type = (AbstractSymbol)visit(current, (static_dispatch)e);}
		else if(e instanceof dispatch){
			debug("Dispatch\n", (dispatch)e);
		    this.containsMethodList(current,(dispatch)e);
			expr_type = (AbstractSymbol)visit(current, (dispatch)e );}
		else if(e instanceof cond)
			expr_type = (AbstractSymbol)visit(current, (cond)e); //ok
		else if(e instanceof loop)
			expr_type = (AbstractSymbol)visit(current, (loop)e); //ok
		else if(e instanceof typcase)
			expr_type = (AbstractSymbol)visit(current, (typcase)e);//ok
		else if(e instanceof block)
			expr_type = (AbstractSymbol)visit(current, (block)e); //ok
		else if(e instanceof let) {
			expr_type = (AbstractSymbol)visit(current, (let)e); }//}}
		else if(e instanceof plus)
			expr_type = (AbstractSymbol)visit(current, (plus)e ); //ok
		else if(e instanceof sub)
			expr_type = (AbstractSymbol)visit(current, (sub)e ); //ok
		else if(e instanceof mul)
			expr_type = (AbstractSymbol)visit(current, (mul)e ); //ok
		else if(e instanceof divide)
			expr_type = (AbstractSymbol)visit(current, (divide)e ); //ok
		else if(e instanceof neg)
			expr_type = (AbstractSymbol)visit(current, (neg)e ); //ok
		else if(e instanceof lt)
			expr_type = (AbstractSymbol)visit(current, (lt)e ); //ok
		else if(e instanceof eq)
			expr_type = (AbstractSymbol)visit(current, (eq)e); //ok
		else if(e instanceof leq)
			expr_type = (AbstractSymbol)visit(current, (leq)e); //ok
		else if(e instanceof comp)
			expr_type = (AbstractSymbol)visit(current, (comp)e); //ok
		else if(e instanceof int_const)
			expr_type = (AbstractSymbol)visit(current, (int_const)e ); //ok
		else if(e instanceof bool_const)
			expr_type = (AbstractSymbol)visit(current, (bool_const)e ); //ok
		else if(e instanceof string_const)
			expr_type = (AbstractSymbol)visit(current, (string_const)e ); //ok
		else if(e instanceof new_)
			expr_type = (AbstractSymbol)visit(current, (new_)e ); //ok
		else if(e instanceof isvoid)
			expr_type = (AbstractSymbol)visit(current, (isvoid)e ); //ok
		else if(e instanceof no_expr)
			expr_type = (AbstractSymbol)visit(current, (no_expr)e ); //ok
		else if(e instanceof object)
		expr_type = (AbstractSymbol)visit(current, (object)e ); //ok

		return expr_type;
	}

	public AbstractSymbol visit(class_c current, cond c) {
		//if pred then then_expr else else_expr fi
		// recupero i tipi delle singole espressioni

		AbstractSymbol if_type;
		AbstractSymbol pred_type = (AbstractSymbol) visit(current, c.pred);
		AbstractSymbol then_type = (AbstractSymbol) visit(current, c.then_exp);
		AbstractSymbol else_type = (AbstractSymbol) visit(current, c.else_exp);

		// il tipo di pred deve essere un boolean
		if(!pred_type.equals(TreeConstants.Bool)){
			cTable.semantError(current.filename, c).println("Predicate of 'if' does not have type Bool.");
			System.exit(0);
		}

		if(then_type.equals(TreeConstants.SELF_TYPE) && else_type.equals(TreeConstants.SELF_TYPE)){
			c.set_type(TreeConstants.SELF_TYPE);
			return TreeConstants.SELF_TYPE;
		}

		// il tipo dell'if e il primo antenato comune
		if(!then_type.equals(TreeConstants.SELF_TYPE) || !else_type.equals(TreeConstants.SELF_TYPE)){
			if(then_type.equals(TreeConstants.SELF_TYPE))
				then_type = current.name;
			if(else_type.equals(TreeConstants.SELF_TYPE))
				else_type = current.name;
			if( then_type.equals(else_type ) ){
				if_type = then_type;
			}else if_type = cTable.nearestCommonAncestor(then_type, else_type);
		}
		else if_type = TreeConstants.SELF_TYPE;

		c.set_type(if_type);
		return if_type;
	}

	public AbstractSymbol visit(class_c current, let l) {
		//let id:type <- init in body
		//scope local table riscritta,  and globaltable non toccare. 
		//recupero il tipo dell'espressione init
		AbstractSymbol init_type = (AbstractSymbol)visit(current, l.init);
		AbstractSymbol lett = null;
		for( int i = 0; i < sorted.size(); i++ )
			if ( sorted.get(i).equals(l.type_decl ) )
				lett = sorted.get(i);

		if(!(l.type_decl.equals(TreeConstants.SELF_TYPE) ||
				(lett != null))){
			cTable.semantError(current.filename,l).println("Class "+ l.type_decl + 
					" of let-bound identifier " + l.identifier + " is undefined.");
			System.exit(0);
		}
		if(!(init_type.equals(TreeConstants.No_type) || cTable.isAncestor(l.type_decl, init_type))){
			cTable.semantError().println(l.lineNumber + ": Type mismatch: " + l.type_decl + " <- " + init_type);
			System.exit(0);
		}


   
		      l.getSymbolTable().addId(l.identifier, l);  
		      globalSymbolTable.add(l.getSymbolTable());  

		//debug("let  in instruction::: ", l);


		AbstractSymbol let_type = (AbstractSymbol) visit(current, l.body);
		l.getSymbolTable().exitScope();
		globalSymbolTable.remove(globalSymbolTable.size()-1);
		l.set_type(let_type);
		return let_type;
	}

	public AbstractSymbol visit(class_c current, dispatch e ){

		AbstractSymbol e0_type = null;
		e0_type = visit( current, e.expr );

		AbstractSymbol dispatch_type = null;

		if ( e.expr instanceof dispatch )
			dispatch_type = checkErrorsOnDispatch(e, e0_type, current, true);
		else dispatch_type = checkErrorsOnDispatch(e, e0_type, current, false);

		
		
		e.set_type(dispatch_type);

		return e.get_type();
	}

	public Object visit(class_c current, static_dispatch sd) {

		AbstractSymbol e0_type = visit( current, sd.expr );
		
		AbstractSymbol dispatch_type = null;

		if( e0_type.equals(TreeConstants.SELF_TYPE) )
			e0_type = current.name;

		dispatch_type = checkErrorsOnDispatch(sd, e0_type, current, false);

		sd.set_type(dispatch_type);

		return sd.get_type();

	}


	private AbstractSymbol checkErrorsOnDispatch(Object e,
			AbstractSymbol e0_type, class_c current, boolean checkhere) {

		static_dispatch sd = null;
		List<class_c> ancestors = null;
		List<AbstractSymbol> actualsType = new LinkedList<AbstractSymbol>();
		List<AbstractSymbol> formalsType = new LinkedList<AbstractSymbol>();
		
		
		List<String> actualsType_str = new LinkedList<String>();
		List<String> formalsType_str = new LinkedList<String>();
		
		
		class_c myclass = null;
		class_c basic = null;
		method current_method = null;
		dispatch d = null;
		class_c parent = null;

		boolean isInCurrent = false;

		if ( e instanceof dispatch ){
			d = (dispatch)e;

			if ( e0_type.equals(TreeConstants.SELF_TYPE) )
				ancestors = cTable.ancestorListwClasses(current.name);
			else ancestors = cTable.ancestorListwClasses(e0_type);

			//enumera anche gli out_string e out_int di IO, nonchè tutti gli altri metodi, in parent e current_method.
			if( ancestors != null){
				for( int i = 0; i < ancestors.size(); i++ ){
					Enumeration<?> features1 = ancestors.get(i).getFeatures().getElements();
					while( features1.hasMoreElements() ){
						Feature _f = (Feature)features1.nextElement();
						if ( _f instanceof method ){
							if ( ((method)_f).name.equals(d.name) ){
								parent = ancestors.get(i);
								//System.out.println("Enumeration feat parent myclass :: " + ancestors.get(i).name.toString() );
								myclass = ancestors.get(i);
								//System.out.println("Enumeration method:: " + ((method)_f).name.toString());
								current_method = (method)_f;							
								break;
							}
						}
					}
				}
			}

			if(parent == null ){
				if (e0_type.equals(TreeConstants.Str) ){
					basic = cTable.getBasicClass(TreeConstants.Str);
				}else if ( e0_type.equals(TreeConstants.Int) ){
					basic = cTable.getBasicClass(TreeConstants.Int);
				}else if (e0_type.equals(TreeConstants.Bool) ){
					basic = cTable.getBasicClass(TreeConstants.Bool);
				}else if ( e0_type.equals(TreeConstants.IO) ){
					basic = cTable.getBasicClass(TreeConstants.IO);
				}
				if( basic == null ){
					cTable.semantError(current.filename, d).println("method "+d.name+" is not found.");
					System.exit(0);
				}
				Enumeration<?> features1 = basic.getFeatures().getElements();
				while( features1.hasMoreElements() ){
					Feature _f = (Feature)features1.nextElement();
					if ( _f instanceof method ){
						if ( ((method)_f).name.equals(d.name) ){
							parent = basic;
							myclass = basic;
							current_method = (method)_f;
							break;
						}
					}

				}
			}

			if ( ancestors == null && basic == null ){
				cTable.semantError(current.filename,d).println("method "+d.name+" is not found!");
				System.exit(0);
			}

			if ( parent == null && !isInCurrent ){
				cTable.semantError(current.filename,d).println("method "+d.name+" is not found!");
				System.exit(0);
			}

			Enumeration<?> actuals = d.actual.getElements();
			AbstractSymbol t = null;
			while( actuals.hasMoreElements() ){
				Expression _e = (Expression)actuals.nextElement();
				if ( _e instanceof dispatch || _e instanceof static_dispatch || checkhere ){
					t = visit(current, _e);
				}else if( myclass.name.equals(TreeConstants.IO) ){
					t = visit( current, _e);
				}else if(!checkhere){
					t = visit( current, _e );//actualsType.add( visit( myclass, _e) );
				}

				if ( t.equals(TreeConstants.SELF_TYPE) )
					t = current.name;
				{actualsType.add(t);
				actualsType_str.add(t.str); }

			}

			Enumeration<?> formals = current_method.formals.getElements();
			while( formals.hasMoreElements() ){
				formalc _form = (formalc)formals.nextElement();
				formalsType.add( _form.type_decl );
				formalsType_str.add(_form.type_decl.str);
			}

			if( actualsType.size() != formalsType.size() ){
				cTable.semantError(current.filename,d).println("Method "+d.name+" called with wrong number of arguments.");
				System.exit(0);
			}

			for ( int j = 0; j < actualsType.size(); j ++ ){
				if ( !(actualsType.get(j).equals(formalsType.get(j))) ){
					if ( !(cTable.isAncestor(formalsType.get(j), actualsType.get(j))) ){
						cTable.semantError(current.filename,d).println("Type of actuals in method "+d.name+" mismatch!");
						System.exit(0);
					}
				}
			}

			//System.out.println("==="+formalsType_str.toString());
			//System.out.println("==="+actualsType_str.toString());

			if ( current_method.return_type.equals(TreeConstants.SELF_TYPE) )
				return e0_type;

			return current_method.return_type;

		}else{
			
			//Controlli per lo static dispatch
			sd = (static_dispatch)e;

			if ( !cTable.isAncestor(sd.type_name, e0_type) ){
				cTable.semantError(current.filename, sd).println("method "+sd.name+" not found in class "+sd.type_name+".");
				System.exit(0);
			}

			ancestors = cTable.ancestorListwClasses(e0_type);

			for ( int i = 0; i < ancestors.size(); i++ ){
				if( sd.type_name.equals(ancestors.get(i).name) ){
					myclass = ancestors.get(i);
					break;
				}
			}

			if( myclass == null ){
				cTable.semantError(current.filename, sd).println("class "+sd.type_name+" is not an ancestor of "+e0_type);
				System.exit(0);
			}

			Enumeration<?> features1 = myclass.getFeatures().getElements();
			while( features1.hasMoreElements() ){
				Feature _f = (Feature)features1.nextElement();
				if ( _f instanceof method ){
					if ( ((method)_f).name.equals(sd.name) ){
						current_method = (method)_f;
						break;
					}
				}
			}

			Enumeration<?> actuals = sd.actual.getElements();
			while( actuals.hasMoreElements() ){
				Expression _e = (Expression)actuals.nextElement();
				if ( _e instanceof dispatch || _e instanceof static_dispatch ){
					actualsType.add( visit(current, _e) );
				}else actualsType.add( visit( myclass, _e) );
			}

			Enumeration<?> formals = current_method.formals.getElements();
			while( formals.hasMoreElements() ){
				formalc _form = (formalc)formals.nextElement();
				formalsType.add( _form.type_decl );
			}

			if( actualsType.size() != formalsType.size() ){
				cTable.semantError(current.filename, sd).println("number paramethers of "+sd.name+" is incorrect!");
				System.exit(0);
			}

			for ( int j = 0; j < actualsType.size(); j ++ ){
				if ( !(actualsType.get(j).equals(formalsType.get(j))) ){
					if ( !(cTable.isAncestor(formalsType.get(j), actualsType.get(j))) ){
						cTable.semantError(current.filename, sd).println(current.lineNumber+":type of actuals in method "+sd.name+" mismatch!");
						System.exit(0);
					}
				}
			}
			if ( current_method.return_type.equals(TreeConstants.SELF_TYPE) )
				return e0_type;

			return current_method.return_type;

		}

	}

	private AbstractSymbol visit(class_c current, assign e){
		// object_id <- expr
		// cerco l'id nella symbol table 
		Object var_type = null;
		List<class_c> ancestors = null;

		for(int i = globalSymbolTable.size()-1; i >= 0; i-- ){
			var_type = globalSymbolTable.get(i).lookup(e.name);
			if ( var_type != null )
				break;
		}
		if ( var_type == null )
			var_type = (attr)current.getSymbolTable().lookup(e.name);

		AbstractSymbol aReturnType = null;

		if ( var_type == null ){
			ancestors = cTable.ancestorListwClasses(current.name);
			for( int i = 0; i < ancestors.size(); i++ ){
				var_type = (attr)ancestors.get(i).getSymbolTable().lookup(e.name);
				if ( var_type != null )
					break;
			}
			if ( var_type == null ){
				cTable.semantError(current.filename, e).println("assignment to undeclared variable " + e.name + "\n prova compilatori    ");
				System.exit(0);
			}
		}else if(var_type.equals(TreeConstants.self)){
			cTable.semantError(current.filename, e).println("cannot assign to \'self\'");
			System.exit(0);
		}

		if( var_type instanceof attr ) aReturnType = ((attr)var_type).type_decl;
		else if( var_type instanceof formalc ) aReturnType = ((formalc)var_type).type_decl;
		else if ( var_type instanceof let ) aReturnType = ((let)var_type).type_decl;
		else if ( var_type instanceof branch ) aReturnType = ((branch)var_type).type_decl;

		AbstractSymbol expr_type = (AbstractSymbol) visit(current, e.expr);


		// perche l'assegnamento sia corretto, expr_type deve essere compatibile con var_type	
		if(!cTable.isAncestor(aReturnType, expr_type)){
			cTable.semantError().println(e.lineNumber + ": type mismatch : " + var_type + " <= " + expr_type);
			System.exit(0);
		}
		
		//System.out.println("assign::: " + aReturnType.str + "  -- " + expr_type.str);

		e.set_type(expr_type);
		return expr_type;
	}

	public AbstractSymbol visit(class_c current, typcase tc) {
		visit(current, tc.expr);
		AbstractSymbol tc_cases_type = (AbstractSymbol)visit(current, tc.cases);

		tc.set_type(tc_cases_type);
		return tc_cases_type;
	}

	public Object visit(class_c current, Cases case_list) {

		Enumeration<?> cases = case_list.getElements();
		LinkedList<AbstractSymbol> order_cases = new LinkedList<AbstractSymbol>();
		
		Set<AbstractSymbol> types = new HashSet<AbstractSymbol>();

		boolean all_self_type = true;
		AbstractSymbol b_type;
		AbstractSymbol common_type;

		branch b = (branch)cases.nextElement();

		common_type = (AbstractSymbol)visit( current, b );
        
		if(!common_type.equals(TreeConstants.SELF_TYPE))
			all_self_type = false;

		types.add(b.type_decl);
		order_cases.add(b.type_decl);
		
		while(cases.hasMoreElements()){

			b = (branch)cases.nextElement();
			b_type = (AbstractSymbol)visit(current, b);
            
			if(!b_type.equals(TreeConstants.SELF_TYPE))
				all_self_type = false;

			if ( !common_type.equals(b_type) ){
				common_type = cTable.nearestCommonAncestor(common_type, b_type); 
				
			    }
			if(types.contains(b.type_decl)){
				cTable.semantError(current.filename, case_list).println("Duplicate branch " + b_type + " in case statement.");
				System.exit(0);
			}else
				{ types.add(b.type_decl); 
				  order_cases.add(b.type_decl);
				}
		}
		
		
		
		if(all_self_type) return TreeConstants.SELF_TYPE;
		return common_type;
	}

	public Object visit(class_c current, branch b) {

		b.getSymbolTable().addId(b.name, b);
		globalSymbolTable.add(b.getSymbolTable());
		AbstractSymbol b_type = (AbstractSymbol)visit(current, b.expr);
		b.getSymbolTable().exitScope();
		globalSymbolTable.remove( globalSymbolTable.size() -1 );

		return b_type;
	}

	public Object visit(class_c current, loop l) {
		// while pred loop body pool

		// visito il predicato e ne recupero il tipo
		AbstractSymbol t = (AbstractSymbol) visit(current, l.pred);

		// il tipo del predicato deve essere un bool
		if(!t.equals(TreeConstants.Bool)){
			cTable.semantError(current.filename, l).println("Loop condition does not have type Bool.");
			System.exit(0);
		}

		// visito il corpo del while
		visit(current, l.body);

		// il tipo di un while e sempre object
		l.set_type(TreeConstants.Object_);
		return TreeConstants.Object_;
	}

	private Object visit(class_c current, block b) {
		AbstractSymbol t = (AbstractSymbol) visit(current, b.body);
		b.set_type(t);
		return t;
	}

	private Object visit(class_c current, Expressions expr_list) {
		Enumeration<?> exprs = expr_list.getElements();
		AbstractSymbol t = TreeConstants.No_type;
		while(exprs.hasMoreElements()){
			t = (AbstractSymbol) visit(current, (Expression)exprs.nextElement());
		}
		
		
		
		return t;
	}


	private AbstractSymbol visit(class_c current, int_const e){

		e.set_type(TreeConstants.Int);
		return TreeConstants.Int;

	}

	private AbstractSymbol visit(class_c current, bool_const e){

		e.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;

	}

	private AbstractSymbol visit(class_c current, string_const e){

		e.set_type(TreeConstants.Str);
		return TreeConstants.Str;

	}

	private AbstractSymbol visit(class_c current, object e ){

		Object as = null;

		if ( e.name.equals(TreeConstants.self) ){
			e.set_type( TreeConstants.SELF_TYPE );
			return e.get_type();
		}



		for(int i = globalSymbolTable.size()-1; i >= 0; i-- ){
			as = globalSymbolTable.get(i).lookup(e.name);
			if( as != null )
				break;
		}

		if ( as == null )
			as = (attr)current.getSymbolTable().lookup(e.name);
		
		AbstractSymbol type = null;
		


		if ( as == null ){
			as = isInherited(current, e);
			
			if ( as == null ){
				cTable.semantError(current.filename, e).println("Undeclared identifier "+e.name + ".");
				System.exit(0);
			}
			
			if(as != null)
			{  System.out.println("Ereditarietà " + current.name + "  " + e.name);}
		}

		if ( as instanceof attr )
			type = ((attr)as).type_decl;
		else if ( as instanceof formalc )
			type = ((formalc)as).type_decl;
		else if ( as instanceof let )
			type = ((let)as).type_decl;
		else if ( as instanceof branch )
			type = ((branch)as).type_decl;

		e.set_type( type );
		return e.get_type();

	}

	public Object visit(class_c current, plus e) {
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current, e.e2);
		if(!(t1.equals(TreeConstants.Int) && t2.equals(TreeConstants.Int))){
			cTable.semantError(current.filename,e).println("non-int arguments:" + t1 + "+" + t2);
			System.exit(0);
		}
		e.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}

	public Object visit(class_c current, sub e) {
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current, e.e2);
		if(!(t1.equals(TreeConstants.Int) && t2.equals(TreeConstants.Int))){
			cTable.semantError(current.filename,e).println("non-int arguments:" + t1 + "+" + t2);
			System.exit(0);
		}
		e.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}

	public Object visit(class_c current, mul e) {
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current, e.e2);
		if(!(t1.equals(TreeConstants.Int) && t2.equals(TreeConstants.Int))){
			cTable.semantError(current.filename,e).println("non-int arguments:" + t1 + "+" + t2);
			System.exit(0);
		}
		e.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}

	public Object visit(class_c current, divide e) {
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current,e.e2);
		if(!(t1.equals(TreeConstants.Int) && t2.equals(TreeConstants.Int))){
			cTable.semantError(current.filename,e).println("non-int arguments:" + t1 + "+" + t2);
			System.exit(0);
		}
		e.set_type(TreeConstants.Int);
		return TreeConstants.Int;
	}

	public Object visit(class_c current, neg e) {
		// ~ e1, negazione aritmetica
		AbstractSymbol t = (AbstractSymbol) visit(current, e.e1);

		// questa operazione puo essere fatta solo su un intero
		if(!t.equals(TreeConstants.Int)){
			cTable.semantError(current.filename, e).println(t + " is not Int.");
			System.exit(0);
		}

		e.set_type(TreeConstants.Int);
		// il tipo di ritorno e un intero
		return TreeConstants.Int;
	}

	public Object visit(class_c current, lt e) {
		// e1 < e2
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current, e.e2);

		// solo due interi possono essere confrontati con <
		if(!(t1.equals(TreeConstants.Int) && t2.equals(TreeConstants.Int))){
			cTable.semantError(current.filename, e).println("non-Int arguments "+t1 + " < "+ t2);
			System.exit(0);
		}
		e.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

	public Object visit(class_c current, eq e) {
		// e1 = e2

		// recupero i tipi delle due espressioni
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current, e.e2);


		/*
		 * Se uno dei due e Int, Str, Bool allora le due espressioni
		 * devono avere lo stesso tipo per essere confrontabili
		 */
		if( t1.equals(TreeConstants.Int) || 
				t1.equals(TreeConstants.Str) ||
				t1.equals(TreeConstants.Bool)||
				t2.equals(TreeConstants.Int) ||
				t2.equals(TreeConstants.Str) ||
				t2.equals(TreeConstants.Bool))

			if(!t1.equals(t2)){
				cTable.semantError(current.filename, e).println(" is not possible to compare type " + t1 + " with type " + t2);
				System.exit(0);
			}

		// il risultato e sempre un bool
		e.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

	public Object visit(class_c current, leq e) {
		// e1 <= e2
		AbstractSymbol t1 = (AbstractSymbol) visit(current, e.e1);
		AbstractSymbol t2 = (AbstractSymbol) visit(current, e.e2);

		// solo due interi possono essere confrontati
		if(!(t1.equals(TreeConstants.Int) && t2.equals(TreeConstants.Int))){
			cTable.semantError(current.filename, e).println(" it's impossible to compare two non-Int Objects.");
			System.exit(0);
		}

		// il tipo di un confronto e sempre un bool
		e.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

	public Object visit(class_c current, comp e) {
		// not e1 , not booleano
		AbstractSymbol t = (AbstractSymbol) visit(current, e.e1);

		// e1 deve essere un'espressione booleana
		if(!t.equals(TreeConstants.Bool)){
			cTable.semantError(current.filename, e).println(t + " is not Bool.");
			System.exit(0);
		}

		// il tipo restituito e sempre bool
		e.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}


	public Object visit(class_c current, new_ n ) {
		// new TYPE_ID

		// se il tipo e SELF_TYPE lo restituisco direttamente
		if(n.type_name.equals(TreeConstants.SELF_TYPE)){
			return n.set_type(TreeConstants.SELF_TYPE);
		}

		// se non esiste stampo l'errore e restituisco no_type
		if( !sorted.contains(n.type_name) ){
			cTable.semantError(current.filename,n).println("'new' used with undefined class "+ n.type_name+".");
			System.exit(0);
		}
		// in caso contrario il tipo e valido e posso restituirlo
		n.set_type(n.type_name);
		return n.type_name;
	}

	private AbstractSymbol visit( class_c current, no_expr e ){
		e.set_type(TreeConstants.No_type);
		return TreeConstants.No_type;
	}

	public Object visit(class_c current, isvoid iv) {
		visit(current, iv.e1);
		iv.set_type(TreeConstants.Bool);
		return TreeConstants.Bool;
	}

	private method isInherited(class_c current, method feature) {
		class_c next = null;
		for (@SuppressWarnings("rawtypes")
		Enumeration e = cls.getElements(); e.hasMoreElements(); ){
			next = (class_c)e.nextElement();
			if ( next.getName().equals( current.getParent() ) ){
				if ( next.getSymbolTable().lookup(feature.name) != null )
					return (method)next.getSymbolTable().lookup(feature.name);
				else return null;
			}
		}
		return null;
	}

	private attr isInherited(class_c current, object o) {
		class_c next = null;
		for (@SuppressWarnings("rawtypes")
		Enumeration e = cls.getElements(); e.hasMoreElements(); ){
			next = (class_c)e.nextElement();
			if ( next.getName().equals(current.getParent() ) ){
				if ( next.getSymbolTable().lookup(o.name) != null )
					return (attr)next.getSymbolTable().lookup(o.name);
				else return null;
			}
		}
		return null;
	}

	private boolean isInherited(class_c current, attr feature) {
		class_c next = null;
		for (@SuppressWarnings("rawtypes")
		Enumeration e = cls.getElements(); e.hasMoreElements(); ){
			next = (class_c)e.nextElement();
			if ( next.getName().equals(current.getParent() ) ){
				if ( next.getSymbolTable().lookup(feature.name) != null )
					return true;
				else return false;
			}
		}
		return false;
	}
	

	private void addMethodList(class_c current, method met, String met1)
	{   
		
		
		if(maft.contains(met.name.toString()))
		{ 				
			cTable.semantError(current).println(met1 + " ==  "+ met.name.toString() +" is not found. Prova compilatori Lab");
		    System.exit(0);
		
		}
		
		m.add(met.name.toString());
		
		System.out.println("addMethodList --> " + m.toString());
		
	}
	
	
	private void containsMethodList(class_c current, static_dispatch met)
	{   //se non si trova in questa lista aggiungilo perchè significa
		//che è stato dichiarato sicuramente, da addMethodList e nella
		// discesa dell'albero è invocato in expr-> dispatch 
		if(!m.contains(met.name.toString()))
	        { maft.add(met.name.toString());  
	        
			System.out.println("containsMethodList--> " + maft.toString());

	        };
	        
	        
		
	}
	
	private void containsMethodList(class_c current, dispatch met)
	{   //se non si trova in questa lista aggiungilo perchè significa
		//che è stato dichiarato sicuramente, da addMethodList e nella
		// discesa dell'albero è invocato in expr-> dispatch 
		if(!m.contains(met.name.toString()))
	        { maft.add(met.name.toString());  
	        
			System.out.println("containsMethodList--> " + maft.toString());

	        };
	        
	        
		
	}

	
	
	private void debug(String str, typcase current){
		
		if(DEBUGMODE){
			System.out.println("=================================");
			System.out.println(str);
			System.out.println("---------------------------------");
            System.out.println("global " + this.globalSymbolTable.toString());
            System.out.println("case.cases" + current.cases.toString());
            System.out.println(" " + globalcount);
            System.out.println("=================================");
            }
		
	}

	
	
	private void debug(String str, dispatch current){
		
		if(DEBUGMODE){
			System.out.println("=================================");
			System.out.println(str);
			System.out.println("---------------------------------");
            System.out.println("global " + this.globalSymbolTable.toString());
            System.out.println("dispatch " + current.name.toString());
            System.out.println(" " + globalcount);
            System.out.println("=================================");
            }
		
	}

	
	
	
	private void debug(String str, attr current){
		
		if(DEBUGMODE){
			System.out.println("=================================");
			System.out.println(str);
			System.out.println("---------------------------------");
            System.out.println("global " + this.globalSymbolTable.toString());
            System.out.println("attr val :: " + current.name.toString());
            System.out.println(" " + globalcount);
            System.out.println("=================================");
            }
		
	}

	
	private void debug(String str, method current){
		
		if(DEBUGMODE){
			System.out.println("=================================");
			System.out.println(str);
			System.out.println("---------------------------------");
            System.out.println("global " + this.globalSymbolTable.toString());
            System.out.println("current " + current.getSymbolTable().toString());
            System.out.println(" " + globalcount);
            System.out.println("=================================");
            }
		
	}

	
	private void debug(String str, class_c current){
		
		if(DEBUGMODE){
			System.out.println("=================================");
			System.out.println(str);
			System.out.println("---------------------------------");
            System.out.println("global " + this.globalSymbolTable.toString());
            System.out.println("current " + current.getSymbolTable().toString());
            System.out.println(" " + globalcount);
            System.out.println("=================================");
            }
		
	}
	
	private void debug(String str, let current){
		
		if(DEBUGMODE){
            System.out.println("=================================");
			System.out.println(str);
			System.out.println("---------------------------------");
            System.out.println("global " + this.globalSymbolTable.toString());
            System.out.println("current " + current.getSymbolTable().toString());
            System.out.println(" " + globalcount);
            System.out.println("=================================");
            }
		
	}

}
