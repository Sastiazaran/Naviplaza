package Agentes;

import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Santa implements Runnable {
    Estados estado;
    ImageIcon img;
    Random r;
    int x;
    int y;
    boolean dead;
    Semaphore s;
    int t = 2000;

    public Santa(int MAXWIDTH, int MAXHEIGHT, Semaphore sem) {
        estado = Estados.PASEANDO;
        r = new Random();
        x = r.nextInt(MAXWIDTH);
        y = r.nextInt(MAXHEIGHT);
        dead = false;
        s = sem;
        t = 5000;
        img = new ImageIcon("./src/Imagenes/image2.png");
    }

    private void Saludando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        estado = Estados.SALUDANDO;
    }

    private void Platicando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        estado = Estados.PLATICANDO;
    }

    private void Posando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        estado = Estados.POSANDO;
    }

    private void Despidiendose() {
        estado = Estados.DESPIDIENDOSE;
    }

    private void Descansando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        estado = Estados.DESCANSANDO;
    }

    @Override
    public void run() {
        while (!dead) {
            Descansando();
            Saludando();
            Platicando();
            Posando();
            Despidiendose();
        }
    }
}
