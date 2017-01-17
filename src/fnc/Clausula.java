package fnc;

import java.util.LinkedList;
import java.util.Objects;

public class Clausula 
{
   private LinkedList<Atomo> atomos= new LinkedList<Atomo>();
    
   public Clausula(){
    }
   
    public Clausula( Clausula c){
        for (Atomo a: c.getAtomos())
            atomos.add(a.Clonar());
    }
   
    public Clausula(Atomo a){
        atomos.add(a);
    }
    
    public Clausula(String name){
        Atomo a=new Atomo(name);
        atomos.add(a);
    }
    
    public Clausula(LinkedList<Atomo> n){
        atomos=n;
    }
    
    public Clausula(String name, boolean signo){
        Atomo a=new Atomo(name,signo);
        atomos.add(a);
    }
    
    public void or(String  nombre,boolean signo){
        Atomo a=new Atomo(nombre,signo);
        atomos.add(a); 
    }
    
    public void or(Atomo  a){
        atomos.add(a);
    }
    
    public void or(Clausula clau) {
        LinkedList<Atomo> n=(LinkedList<Atomo>)clau.getAtomos().clone();
        for(Atomo a:n)
        atomos.add(a);
    }

   @Override
    public String toString(){
        String str="";
        str+="(";
        if(atomos.size()==0)
            return str+=")";
          else
        if(atomos.size()==1)
            str+=atomos.getLast().toString();
        else{
            int i=0;
            do{
                str+=atomos.get(i).toString()+"|";
            }while(i++<atomos.size()-2);
            str+=atomos.getLast();
        }   
        str+=")";
        return str;
    }

    public LinkedList<Atomo> getAtomos(){
        return atomos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;
        
        if (getClass() != obj.getClass()) 
            return false;
        
        final Clausula other = (Clausula) obj;
        if (!Objects.equals(this.atomos, other.getAtomos()))
            return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.atomos);
        return hash;
    }
    
    public Formula negar(){
        Formula f = new Formula();
        for (Atomo atomo : atomos) {
            f.and(atomo.negar2());
        }
        return f;
    }
    
    public Clausula Clonar()
    {
        LinkedList<Atomo> owo=new LinkedList<>();
        for(Atomo a:getAtomos())
        {
            owo.add(a.Clonar());
        }
        Clausula novo=new Clausula(owo);
        return novo;
    }
    
    public boolean Tauto(){   
        int c=0;
        do{
            Atomo check=atomos.get(c);
            for(Atomo a:getAtomos())
                if(check.getNombre().equals(a.getNombre()) && check.isNegado()!=a.isNegado())
                    return true;
        }while(c++<atomos.size()-1);
        return false;
    }
    
    public Atomo Tauto2(){
        int c=0;
        do
        {
            Atomo check=atomos.get(c);
            for(Atomo a:getAtomos())
            {
                if(check.getNombre().equals(a.getNombre()) && check.isNegado()!=a.isNegado())
                {
                    return check;
                }
            }
            
        }
        while(c++<atomos.size()-1);
        
        return new Atomo("x");
    }
  
    public boolean unitaria(){
       return atomos.size()==1;
    }
    
    public boolean hayVacia(){
        return atomos.size()==0;
    }
    
    public void negarBi(Atomo a){
        for (Atomo atomo : atomos) {
            if (atomo.getNombre().equals(a.getNombre())){
                atomo.negar();
            }
        }
    }
    
    
  
}