package fnc;

import java.util.Objects;

public class Atomo{
    String nombre;
    Boolean signo;
    int bifurcacion;
    int turno;
    
    public Atomo(String nombre) {
        this.nombre = nombre;
        this.signo=false;
        this.bifurcacion=0;
        this.turno=0;
    }
    
    public Atomo(){}

    public String getNombre() {
        return nombre;
    }

    public Atomo(String nombre, Boolean signo){
        this.nombre = nombre;
        this.signo = signo;
        this.bifurcacion=0;
        this.turno=0;
    }
    
    public void negar(){
        if(signo==false)
            signo=true;
          else
            signo=false;
    }
    
    public Atomo negar2(){
        if(signo==false)
            signo=true;
          else
            signo=false;        
        return Clonar();
    }
    
    public void setBi(int a){
        this.bifurcacion=a;
    }
    
    public void BiMas(){
        this.bifurcacion+=1;
    }
    
    public void setTurno( int a){
        this.turno=a;
    }
    
    public int getTurno(){
        return turno;
    }
    
    public int getB(){
        return bifurcacion;
    }
    
    @Override
    public String toString(){
        if(signo==true)
            return "~"+nombre;
          else
            return nombre;
    }
    
    public boolean isNegado(){
        return signo;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;
        
        if (getClass() != obj.getClass()) 
            return false;
        
        final Atomo other = (Atomo) obj;
        if (!Objects.equals(this.nombre, other.nombre))
            return false;      
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.signo);
        return hash;
    }
    
    public Atomo Clonar(){
        return new Atomo(this.nombre, this.signo);
    }
}