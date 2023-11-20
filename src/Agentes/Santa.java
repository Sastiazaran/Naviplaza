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
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setEstado(Estados.SALUDANDO);
    }

    private void Platicando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setEstado(Estados.PLATICANDO);
    }

    private void Posando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setEstado(Estados.POSANDO);
    }

    private void Despidiendose() {
        setEstado(Estados.DESPIDIENDOSE);
    }

    private void Descansando() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setEstado(Estados.DESCANSANDO);
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
