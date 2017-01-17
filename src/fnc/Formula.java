package fnc;

import com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl;
import java.util.ArrayList;
import java.util.LinkedList;

public class Formula{
    private LinkedList<Clausula> clausulas=new LinkedList<Clausula>();
    private LinkedList<Atomo> valores=new LinkedList<>();
    private LinkedList<Atomo> valoresfin=new LinkedList<>();
    private LinkedList<Atomo> atomos=new LinkedList<>();
    private LinkedList<Clausula> pb= new LinkedList<Clausula>();
    LinkedList<Clausula> losAt= new LinkedList<Clausula>();
    private ArrayList<LinkedList<Clausula>> numBifu=new ArrayList<>();
    boolean cuenta=false;
    int val[] = {0,0,0,0,0,0,0,0,0,0};
    int cont=0;
    boolean entro=false;
    
    public Formula(String s){
        Atomo atm=new Atomo(s,false);
        clausulas.add(new Clausula(atm));
        if (esta(atm))
            valoresfin.add(atm.Clonar());
        
    }
    
    public Formula(LinkedList<Clausula> s){
        for (Clausula c: s){
            for (Atomo a: c.getAtomos()){
                if (!esta(a)){
                    valoresfin.add(a.Clonar());
                    atomos.add(a.Clonar());
                }
            }
        }
       clausulas=(LinkedList<Clausula>)s.clone();     
    }
    
    public Formula(){
    }
    
    public Formula(Atomo atm){
        Clausula cl=new Clausula(atm);
        clausulas.add(cl);
        
    }
    
    public Formula(String nombre,boolean signo){
        Clausula cl=new Clausula(nombre,signo);
        for (Atomo a: cl.getAtomos()){
            if (!esta(a))
                valoresfin.add(a.Clonar());
        }
        clausulas.add(cl);
    }
    
    public Formula(Clausula cl){
        for (Atomo a: cl.getAtomos()){
            if (!esta(a))
                valoresfin.add(a.Clonar());
        }
        clausulas.add(cl);
    }
    
    public void and(String nombre,boolean signo){
        clausulas.add(new Clausula(nombre,signo));
    }
    
    public void and(Atomo a){
        clausulas.add(new Clausula(a));
    }
    
    public void and(Clausula clau){
        clausulas.add(clau);
        
    }
    
    public void and (Formula f){
        for (Clausula c: f.clausulas)
            clausulas.add(c);
    }
    
    public void or(Clausula c){
        for(Clausula a:clausulas)
            a.or(c);
    }
    
    public Formula or(Formula f){
        LinkedList<Clausula>tempo=new LinkedList<Clausula>();
        for(Clausula a:getClausulas()){
            for(Clausula b:f.getClausulas()){
                Clausula nueva=new Clausula((LinkedList<Atomo>)a.getAtomos().clone());
                nueva.or(b);
                tempo.add(nueva);
            }
        }
        clausulas.clear();
        clausulas=tempo;
        return this;
    }
    
    @Override
    public String toString(){
        String str="";
        str+="[";
        if(clausulas.size()==0)
            return str+="]";
          else
        if(clausulas.size()==1)
            str+=clausulas.getFirst().toString();
          else{
            int i=0;
            do{
                str+=clausulas.get(i).toString()+" & ";
            }while(i++<clausulas.size()-2);
            str+=clausulas.getLast().toString();
        }
        str+="]";
        return str;
    }
    
    public LinkedList<Clausula> getClausulas(){
        return clausulas;
    }
    
    public Formula not() {
        Clausula  clausula = new Clausula();
        int n = clausulas.size();
        Formula forula[] = new Formula[n],f =null;
        for (int i = 0; i < n; i++) {
            clausula = new Clausula(clausulas.get(i));
            forula[i] = clausula.negar();
        }
        f = forula[0];
        for (int i = 1; i < n; i++) {
            f = f.or(forula[i]);
        }
        clausulas = f.getClausulas();
        return f;
    }   
    
    ////Tautología 
    public boolean Tautologia(){
        LinkedList<Clausula> nueva=(LinkedList<Clausula>)getClausulas().clone();
        int i=0;
        
        boolean a=false;
        for(Clausula clau:getClausulas()){
            if(clau.Tauto()){
                nueva.remove(i);
                a=true;
            }
            i++;
        }
        getClausulas().clear();
        clausulas=nueva;
      //  if (a)
      //      System.out.println("F:("+valores.toString()+")=\t"+toString());
        return a;
    }
    
    //Clausula Unitaria
    public boolean unitaria(){
        boolean a=false;
        for(Clausula clau:getClausulas()){
            if(clau.unitaria()){
                unitariaTool(clau.getAtomos().getFirst());
                a=true;
                break;
            }
        }
      //  if (a)
       //     System.out.println("F::("+valores.toString()+")=\t"+toString());
        return a;
    }
    
    public void unitariaTool(Atomo uni){
        LinkedList<Clausula> nueva=clonar();
        int i=0;
        boolean ban=false;
        for(Clausula clau:getClausulas()){
            for(Atomo a:clau.getAtomos()){
                if(a.getNombre().equals(uni.getNombre()) && a.isNegado()==uni.isNegado()){
                    if(ban==false)
                    {
                        valores.add(a);
                        val[cont]++;
                        ban=true;
                    }
                    nueva.remove(clau);
                    i--;
                }
                  else
                    if(a.getNombre().equals(uni.getNombre())){
                        Clausula n=nueva.get(i);
                        n.getAtomos().remove(a);
                    }
            }
            i++;
        }
        clausulas.clear();
        clausulas=nueva;
    }
    
    //Literal Pura
    public boolean Literal(){
        LinkedList<Clausula> nueva=clonar();
        boolean res=false;
        for(Clausula clau:nueva){
            for(Atomo a:clau.getAtomos()){
                if (Literal2(a,clau)){
                    res=true; ///
                    valores.add(a);
                    val[cont]++;
                    Literal3(a);
                }
                if(res)
                    break;
            }
            if (res)
                break;
        }
    //    if (res)
      //      System.out.println("F:::("+valores.toString()+")=\t"+toString());
        return res;
    }
    
    public boolean Literal2 (Atomo a, Clausula c){
        boolean y=true;
        for(Clausula clau: getClausulas())
            for(Atomo at:clau.getAtomos())
                if(at.getNombre().equals(a.getNombre()) && at.isNegado()!=a.isNegado()){
                    y=false;
                    break;
                }
        return y;
    }
    
    public void Literal3(Atomo at){
        LinkedList<Clausula> nueva=clonar();
        for(Clausula clau:getClausulas())
            for(Atomo a:clau.getAtomos())
                if(a.getNombre().equals(at.getNombre()) && a.isNegado()==at.isNegado())
                    nueva.remove(clau);
        clausulas.clear();
        clausulas=nueva;
    }
    
    
    //Pasar sin parametros se usa cuando se usa cuando la solución no se encontró en un cámino de la bifurcación
    public void Pasar(){
      //  System.out.println("Valores sin solución:("+valores.toString()+")");
        clausulas.clear();
        clausulas=numBifu.get(cont-1); 
       for(int i=0;i<val[cont];i++){
           valores.removeLast();
       }
        getClausulas().getFirst().getAtomos().getFirst().setBi(2);
        pb=clonar();
        pb.getFirst().getAtomos().getFirst().setBi(2);
     //   System.out.println("Cambia de valor en la Bifurcación\n Formula\n"+toString());
        bifurcacion(getClausulas().getFirst().getAtomos().getFirst());
        cuenta=true;
    }
    
    //Es el que se manda a llamar para iniciar la Bifurcación 
    public boolean Pasar(String k){
        getClausulas().getFirst().getAtomos().getFirst().setBi(1);
        numBifu.add(clonar());  
        cont++;
        pb= clonar();
        boolean sol;
        sol = bifurcacion(getClausulas().getFirst().getAtomos().getFirst());
        return sol;
    }
    
    public boolean bifurcacion(Atomo b){
        LinkedList<Clausula> Tempo=clonar();
        LinkedList<Clausula> Tempo2=clonar();
        int i=0;
        Atomo bb=b;
        boolean ban=false;
        if (b.getB() <=2){
            if (b.getB()==2){    
                bb=b.negar2();
                b.negar();
            }
            boolean valo=false;
            for(Clausula clau:getClausulas()){
                for(Atomo a:clau.getAtomos()){
                    if(a.isNegado()==bb.isNegado() && a.getNombre().equals(bb.getNombre())){
                        if(ban==false){
                            if (valo==false){
                                valores.add(a);
                                val[cont]++;
                                valo=true;
                            }
                        }
                        Tempo.remove(clau);
                        i--;
                    }
                    else
                        if(a.getNombre().equals(bb.getNombre())){
                            Clausula n=Tempo.get(i);
                            n.getAtomos().remove(a);
                        }
                }
                i++;
            }
            clausulas.clear();
            clausulas=Tempo;
        //    System.out.println("F::::("+valores.toString()+")=\t"+toString());
            ban = formulaVacia();
        }
        return ban;
    }
    
    public boolean Davis(){
        losAt = clonar();
        Tautologia();
        return Putnam();
    }
    
    public boolean Putnam(){
        int nBifurcacion=0;
        boolean bifu=false;
        boolean solucion, aplica;
        if (clausulaVacia()){
            if (!(pb.size()==0)){
                if (pb.getFirst().getAtomos().getFirst().getB()==2){
                    solucion= false;
                }
                else{
                    Pasar();
                    solucion= Putnam();
                }
            }
              else
                solucion= false;
        }
          else{
            if (formulaVacia()){
                solucion= true;
            }
            else{
                if(unitaria())
                    aplica=true;
                else
                    if (Literal())
                        aplica=true;
                      else
                        aplica=false;
                
                if (aplica){
                    solucion= Putnam();
                }
                else{
                    bifu=true;
                    Pasar("Primer Bifurcación");
                    solucion= Putnam();
                }
            }
          }
       return solucion;
    }

    public boolean gSat(int intentos, int cambios){
     //   System.out.println("Original"+toString());
        pb= clonar();
        Aleatorio();
   //     System.out.println("Primeros Valores"+atomos.toString());
        
        return gSat2(intentos, cambios);
    }
    //(p>~q|p)&p&(~q|p)&~q
    public boolean gSat2(int a, int b){
        
        boolean sol=false, otro=false, otro2=false;
        for(int i=0;i<a;i++){
            for (int j=0;j<b;j++){
                LinkedList<Clausula> Tempo=clonar();
                Tautologia();
                for (Atomo at:atomos){
                    nuevaSolucion(at);  
                }
                /////////////////////////
           //     System.out.println("Es"+toString());
             //   System.out.println("Cambiar Valores"+atomos.toString());
                if (clausulaVacia()){
                    clausulas.clear();
                    clausulas= Tempo;
                    sol=false;
               //     System.out.println("Es"+toString());
                }
                  else{
                    if (formulaVacia()){
                        sol= true;
                    }
                  }
                ////////////////////////             
                if (sol)
                    break;
                  else
                    Aleatorio2();
            }
            if (sol)
                    break;
                  else
                    Aleatorio();
        }
 //       System.out.println("Final"+toString());
   //     System.out.println("Valores finales"+atomos.toString());
        valores=atomos;
        return sol;
    }
    
    
    public boolean random(){
        if(((int)(Math.random()*(3-1))+1)==1)
            return true;
           else
            return false;
    }
    
    public void Aleatorio(){
        boolean este=false;
        for (Atomo atomo: atomos ){
            if (random()){
                atomo.negar();
                este=true;
            }
        }
        if (!este)
            Aleatorio();
    }
    
    
    public void Aleatorio2(){
        boolean este=false;
        for (Atomo atomo: atomos ){
            if (random()){
                atomo.negar();
                este=true;
                break;
            }
        }
        if (!este)
            Aleatorio2();
    }
    
    public LinkedList<Clausula> clonar(){
        LinkedList<Clausula> clau=new LinkedList<>();
        for(Clausula l:getClausulas())
            clau.add(l.Clonar());
        return clau;
    }
    
    public boolean formulaVacia(){  //antes check
        return getClausulas().isEmpty();
    }
        
    public boolean clausulaVacia(){
        for(Clausula clau:getClausulas()){
            if (clau.hayVacia())
                return true;
        }
        return false;
    }
    
    public boolean hayUni(){
        if (unitaria())
            return true;
         else
            return false;
    }
    
    public int CuentaUni(){
        int cont=0;
        for (Clausula c : clausulas) {
            if (c.getAtomos().size()==1 ){
                cont++;
            }
        }
        return cont;
    }
        
    public boolean esta(Atomo a){
        for (Atomo at : valoresfin) {
            if (at.getNombre().equals(a.getNombre()))
                return true;
            }
        return false;
   }
    
    public boolean esta2(Atomo a){
        for (Atomo at : valores) {
            if (at.getNombre().equals(a.getNombre()))
                return true;
            }
        return false;
   }
    
    
    public String Mostrar(){
        String resultado="Si tiene solución\n";
        for (Atomo atomo : valores) {
            resultado+=atomo.getNombre();
            if (atomo.isNegado())
                resultado+=": False\n";
              else
                resultado+=": True\n";
        }
        for (Atomo atomo: valoresfin){
            if (!esta2(atomo)){
                resultado+=atomo.getNombre();
                resultado+=": No importa\n";
            }
            
        }
        return resultado;
    }
    
    public void nuevaSolucion(Atomo bb){
        LinkedList<Clausula> Tempo=clonar();
        int i=0;
        for(Clausula clau:getClausulas()){
                for(Atomo a:clau.getAtomos()){
                    if(a.isNegado()==bb.isNegado() && a.getNombre().equals(bb.getNombre())){
                        Tempo.remove(clau);
                        i--;
                        break;
                    }
                    else
                        if(a.getNombre().equals(bb.getNombre())){
                            Clausula n=Tempo.get(i);
                            n.getAtomos().remove(a);
                        }
                }
                i++;
            }
            clausulas.clear();
            clausulas=Tempo;
            
    }
}