package Agentes;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Santa extends Agentes {
    private Semaphore santaS;
    private boolean clienteEsperando;

    public Santa(int MAXWIDTH, int MAXHEIGHT, Semaphore semSanta, int t) {
        super(MAXWIDTH, MAXHEIGHT, "santa");
        setEstado(Estados.DESCANSANDO);
        this.t = t;
        santaS = semSanta;
        clienteEsperando = false;
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

    private int decidirQueHacer() {
        return r.nextInt(2);
    }

    @Override
    public void run() {
        while (!unavailable()) {
            Descansando();
            if (unavailable())
                break;
            int d = decidirQueHacer();
            if (d == 0) {
                try {
                    santaS.acquire();
                    Saludando();
                    if (unavailable())
                        break;
                    Platicando();
                    if (unavailable())
                        break;
                    Posando();
                    if (unavailable())
                        break;
                    Despidiendose();
                    if (unavailable())
                        break;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                santaS.release();
            }
        }

    }

    public void cliente(String name) {
        clienteEsperando = true;
    }
}
