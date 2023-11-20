package Agentes;

import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Cliente implements Runnable {
    Estados estado;
    ImageIcon img;
    Random r;
    int x;
    int y;
    boolean dead;
    Semaphore s;
    int t;

    public Cliente(int MAXWIDTH, int MAXHEIGHT, Semaphore sem) {
        estado = Estados.PASEANDO;
        r = new Random();
        x = r.nextInt(MAXWIDTH);
        y = r.nextInt(MAXHEIGHT);
        dead = false;
        s = sem;
        t = 5000;
        img = new ImageIcon("./src/Imagenes/image2.png");
    }

    private void enPlaza() {
        if (estado == Estados.PASEANDO) {
            if (r.nextInt(2) == 0) {
                // ESPERA A SANTA
                try {
                    s.acquire();
                    estado = Estados.ESPERANDO;
                    wait(t);
                    s.release();
                } catch (Exception e) {
                }
            } else {
                // BUSCA REGALOS
                // estado = CO ? FIX DIAGRAM FIRST
            }
        }
    }

    @Override
    public void run() {
        while (!dead) {
            enPlaza();
        }
    }
}
