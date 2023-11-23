package Agentes;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Santa extends Agentes {

    public Santa(int MAXWIDTH, int MAXHEIGHT, Semaphore[] sem) {
        super(MAXWIDTH, MAXHEIGHT, sem, "santa");
        setEstado(Estados.DESCANSANDO);
        t = 2000;
        img = new ImageIcon("./src/Imagenes/image2.png");
    }

    private void Saludando() {
        setEstado(Estados.SALUDANDO);
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void Platicando() {
        setEstado(Estados.PLATICANDO);
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void Posando() {
        setEstado(Estados.POSANDO);
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void Despidiendose() {
        setEstado(Estados.DESPIDIENDOSE);
        sleep();
    }

    private void Descansando() {
        setEstado(Estados.DESCANSANDO);
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private int decidirQueHacer(){
        return r.nextInt(2);
    }

    @Override
    public void run() {
        while (!unavailable()) {
            Descansando();
            if(unavailable()) break;
            int d = decidirQueHacer();
            if(d==0){
                Saludando();
                if(unavailable()) break;
                Platicando();
                if(unavailable()) break;
                Posando();
                if(unavailable()) break;
                Despidiendose();
                if(unavailable()) break;
            }
        }

    }
}
