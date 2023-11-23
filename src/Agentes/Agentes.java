package Agentes;

import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public abstract class Agentes implements Runnable {
    Estados estado;
    ImageIcon img;
    Random r;
    int x;
    int y;
    boolean dead;
    Agentes interactuaCon;
    Semaphore[] s;
    int t;
    String name;
    String type;
    String secCrit;
    String buffer;
    
    public Agentes(int MAXWIDTH, int MAXHEIGHT, Semaphore[] sem, String t){
        r = new Random();
        x = r.nextInt(MAXWIDTH);
        y = r.nextInt(MAXHEIGHT);
        setDead(false);
        setBuffer("none");
        setSecCrit("none");
        s = sem;
        type = t;
    }
    
    public void setName(String s){
        name = s;    
    }
    
    public String getName() {
        return name;
    }

    public String getEstado() {
        return estado.name();
    }
    
    public void setEstado(Estados estado) {
        this.estado = estado;
    }
    
    public String getSecCrit() {
        return secCrit;
    }

    public void setSecCrit(String secCrit) {
        this.secCrit = secCrit;
    }
    
    public String getBuffer() {
        return buffer;
    }
    
    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isPanic(){
        if(estado==Estados.PANICO){
            return true;
        }
        return false;
    }
    
    public void setDead(boolean dead) {
        this.dead = dead;
        setBuffer("none");
        setSecCrit("none");
        setEstado(Estados.MUERTO);
    }

    public String getDeadString(){
        return String.valueOf(dead);
    }

    public void sleep(){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean unavailable(){
        if(isDead() || isPanic()) return true;
        return false;
    }

    public String getEstado(Estados e) {
        if (estado == e) {
            return "X";
        }
        return " ";
    }
}
