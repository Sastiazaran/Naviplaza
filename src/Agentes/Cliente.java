package Agentes;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Cliente extends Agentes {

    public Cliente(int MAXWIDTH, int MAXHEIGHT, Semaphore[] sem) {
        super(MAXWIDTH, MAXHEIGHT, sem, "cliente");
        setEstado(Estados.PASEANDO);
        t = 5000;
        img = new ImageIcon("./src/Imagenes/image2.png");
    }

    private void enPlaza() {
        if (estado == Estados.PASEANDO) {
            if (r.nextInt(2) == 0) {
                // ESPERA A SANTA
                try {
                    s[0].acquire();
                    setEstado(Estados.ESPERANDOSANTA);
                    wait(t);
                    s[0].release();
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
