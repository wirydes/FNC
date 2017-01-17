package fnc;

import static fnc.Postfijo.dev;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Stack;

public class Postfijo { 
    //Obtiene el postfijo de la entrada de texto
    
    public static String[] postfijo(String cadena, int aa, int cc){
        intentos=aa;
        cambios=cc;
   //     System.out.println("Infijo: "+cadena);
        //Empieza el ciclo con el infijo
        do{
            String ch=cadena.charAt(0)+"";
            cadena=cadena.substring(1);
            //si es un parentesis de apertura se mete a la pila todo el tiempo
            if(ch.equals("(")){
                p.push(ch);
                if (!postfijo.equals("") && postfijo.charAt(postfijo.length()-1)!=' ')
                    postfijo+=", ";
            }
              else
                //si es parentesis de cerrado se barre la pila hasta encontrar el otro parentesis
                if(ch.equals(")")){
                    while(!"(".equals(p.peek()))
                        postfijo+=", "+ p.pop();
                    p.pop();
                }
                  else
                    //si es un operador se debe verificar la prioridad para insertarlo
                    if(no.contains(ch)){
                        if (!postfijo.equals("") && postfijo.charAt(postfijo.length()-1)!=' ')
                            postfijo+=", ";
                        if(p.isEmpty())
                            p.push(ch);
                          else{
                            String caca=p.peek()+"";
                            if(cimaMayorIgual(caca,ch))
                                p.push(ch);
                            else{
                                do{
                                    postfijo+=p.pop()+", ";
                                    if (p.isEmpty())
                                        break;
                                }while(!cimaMayorIgual(p.peek()+"",ch));
                                p.push(ch);
                            }
                        }
                    }
                      else
                        postfijo+=ch;
        }
        while(cadena.length()>0 );
        
        //Al salir del ciclo se debe vaciar la pila y meterla a postfijo
        do{
            if(!p.isEmpty())
                postfijo+=", "+p.pop();
        }while(!p.empty());
        String n="";
        for (int x=0; x < postfijo.length(); x++) {
            if (postfijo.charAt(x) != ' ')
                n += postfijo.charAt(x);
        }    
        postfijo=n;
   //     System.out.println("Postfijo: "+postfijo);
        dev[0]=postfijo;
        String ar[] = postfijo.split(",");
        postfijo="";
        evaluar(ar, ar.length);
        return dev;
    }
    //Retorna true si el ch tiene mayor prioridad que la peek
    private static boolean cimaMayorIgual(String peek, String el) {
        int pc=0, pp=0;
        if(peek.equals("("))
            return true;
         else
            if(peek.equals(el))
                return true;
              else{
                switch (el){
                    case "=":pc=1;break;
                    case ">":pc=2;break;
                    case "|":pc=3;break;
                    case "&":pc=4;break;
                    case "~":pc=5;break;
                }
                switch (peek){
                    case "=":pp=1;break;
                    case ">":pp=2;break;
                    case "|":pp=3;break;
                    case "&":pp=4;break;
                    case "~":pp=5;break;
                }
                //si el ch es mayor que el peek
                if(pc>=pp)
                    return true;
                  else
                    return false;
              }
    }
    //Evalúa el postfijo
    private static void evaluar(String[] postfijo, int tam){
        for (int i=0;i<tam;i++){
            if(!no.contains(postfijo[i]))
                pipila.push(new Formula(postfijo[i]));
              else{
                switch(postfijo[i]){
                    case "~": Formula tem = new Formula(pipila.pop().getClausulas());
                              tem.not();
                              pipila.push(tem);
                              break;
                    case "|": Formula tem1=new Formula(pipila.pop().getClausulas());
                              Formula tem2=new Formula(pipila.pop().getClausulas());
                              tem2.or(tem1);
                              pipila.push(tem2);
                              tem1=null;
                              tem2=null;
                              break;
                    case "&": tem1=new Formula(pipila.pop().getClausulas());
                              tem2=new Formula(pipila.pop().getClausulas());
                              tem2.and(tem1);
                              pipila.push(tem2);
                              tem1=null;
                              tem2=null;
                              break;
                    case ">": tem1=new Formula(pipila.pop().getClausulas());
                              tem2=new Formula(pipila.pop().getClausulas());
                              tem2.not();
                              tem2.or(tem1);
                              pipila.push(tem2);
                              tem1=null;
                              tem2=null;
                              break;
                    case "=": tem2=new Formula(pipila.pop().getClausulas());
                              tem1=new Formula(pipila.pop().getClausulas());
                              Formula tem22=new Formula();
                              int cono=0;
                              do{
                                  LinkedList<Atomo> novo=new LinkedList<Atomo>();
                                  for(Atomo a:tem2.getClausulas().get(cono).getAtomos()){
                                      novo.add(new Atomo(a.nombre,a.signo));
                                  }
                                  Clausula noob2=new Clausula(novo);
                                  tem22.getClausulas().add(noob2);
                              }while(cono++<tem2.getClausulas().size()-1);
                              int con=0;
                              Formula tem11=new Formula();
                              do{
                                  LinkedList<Atomo> novo2=new LinkedList<Atomo>();
                                  for(Atomo a:tem1.getClausulas().get(con).getAtomos()){
                                      novo2.add(new Atomo(a.nombre,a.signo));
                                  }
                                  Clausula noob=new Clausula(novo2);
                                  tem11.getClausulas().add(noob);
                              }while(con++<tem1.getClausulas().size()-1);
                              tem1.not();
                              tem1.or(tem2);  
                              tem22.not();
                              tem22.or(tem11);
                              tem1.and(tem22);
                              pipila.push(tem1);
                              tem1=null;
                              tem2=null;
                              tem11=null;
                              tem22=null;
                              break;
                }
            }
            
        }
        Formula tem = new Formula(pipila.pop().getClausulas());
        dev[1]=tem.toString();
        if (intentos==-1 && cambios==-1){
            if (tem.Davis())
                dev[2]=tem.Mostrar();
              else
                dev[2]="No tiene solución";
            dev[3]="BIFURCACIÓN";
        }
          else{
                if(tem.gSat(intentos, cambios))
                    dev[2]=tem.Mostrar();
                  else
                    dev[2]="No se encontro solución";
            dev[3]="gSat";
        }
    }
    //Retorna el archivo txt
    public static void escribir(String in, String pos, String form) throws FileNotFoundException{
    //    InetAddress localHost = InetAddress.getLocalHost();
       File fichero1 = new File(getPath()+"\\Resultados.txt");
        PrintWriter fs1 = new PrintWriter (fichero1);
        fs1.write("Infijo:");
        fs1.println();
        fs1.write(in);
        fs1.println();
        fs1.write("Postfijo:");
        fs1.println();
        fs1.write(pos);
        fs1.println();
        fs1.write("Formula:");
        fs1.println();
        fs1.write(form);
        fs1.println();
        fs1.close();
    }
    
    public static String getPath() {
    try {
      Process process = Runtime.getRuntime().exec(DESKTOP_FOLDER_CMD);
      StreamReader reader = new StreamReader(process.getInputStream());

      reader.start();
      process.waitFor();
      reader.join();
      String result = reader.getResult();
      int p = result.indexOf(REGSTR_TOKEN);

      if (p == -1) return null;
      return result.substring(p + REGSTR_TOKEN.length()).trim();
    }
    catch (Exception e) {
      return null;
    }
  }
    
    //Variables 
    static int intentos=-1, cambios=-1;
    static String dev[] = new String [4];
    static String no ="~|&>=";
    static String postfijo="";
    static String formula="";
    static Stack <String> p = new Stack<String>();
    static Stack <Formula> pipila= new Stack<Formula>();
    
    private static final String REGQUERY_UTIL = "reg query ";
    private static final String REGSTR_TOKEN = "REG_SZ";
    private static final String DESKTOP_FOLDER_CMD = REGQUERY_UTIL 
     + "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\" 
     + "Explorer\\Shell Folders\" /v DESKTOP";
}