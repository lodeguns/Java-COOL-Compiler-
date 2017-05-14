class A inherits IO { 

      attr1:Int <- 5;

      pix(m:Int):Int{ {out_string("Prova compilatori nella Classe A:: \n");
                       out_int(m);
                       out_string("\n");
                       m;}};

};


class Main inherits IO {


    a:Int;
    
     b:A;
    
          
  
  

    
    main() : SELF_TYPE{
     { 
     
       {    a <- 3;
       
            pix(a);
            
            
            b.pix(a);
 
   
        
        self;
        };
        
       } --block main()
       }; --main()
    

  pix(m:Int):Int{ {out_string("Prova compilatori nel Main:: \n");
                       out_int(m);
                       out_string("\n");
                       m;}};

};
