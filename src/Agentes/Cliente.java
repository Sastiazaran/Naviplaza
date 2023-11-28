package Agentes;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Cliente extends Agentes {

    public Cliente(int MAXWIDTH, int MAXHEIGHT, Semaphore[] sem, int t) {
        super(MAXWIDTH, MAXHEIGHT, sem, "cliente");
        setEstado(Estados.PASEANDO);
        this.t = t;
        img = new ImageIcon("./src/Imagenes/image2.png");
    }

    private void paseando() {
        setEstado(Estados.PASEANDO);
        setBuffer("none");
        setSecCrit("none");
        sleep();
    }

    private int decidirQueHacer() {
        return r.nextInt(3);
    }

    private void esperarSanta() {
        setEstado(Estados.ESPERANDOSANTA);
        setBuffer("Fila de Santa");
        setSecCrit("none");
        sleep();
    }

    private void conSanta() {
        setEstado(Estados.CONVIVIENDO);
        setBuffer("none");
        setSecCrit("Con Santa");
        sleep();
    }

    private boolean verRegalos() {
        setEstado(Estados.VIENDOREG);
        setBuffer("none");
        setSecCrit("Con Vendedora");
        sleep();
        return (r.nextInt(2) == 0);
    }

    private void escogiendo() {
        setEstado(Estados.ESCOGIENDOREG);
        setBuffer("none");
        setSecCrit("Con Vendedora");
        sleep();
    }

    private boolean quiereEnvoltura() {
        return (r.nextInt(2) == 0);
    }

    private void envoltura() {
        setEstado(Estados.ESPERANDOENVOLTURA);
        setBuffer("none");
        setSecCrit("Con Vendedora");
        sleep();
    }

    private void pagar() {
        setEstado(Estados.PAGANDO);
        setBuffer("none");
        setSecCrit("Con Vendedora");
        sleep();
    }

    @Override
    public void run() {
        while (!unavailable()) {
            paseando();
            if (unavailable())
                break;
            int d = decidirQueHacer();
            if (unavailable())
                break;
            switch (d) {
                case 0:
                    esperarSanta();
                    if (unavailable())
                        break;
                    conSanta();
                    break;
                case 1:
                    if (verRegalos()) {
                        if (unavailable())
                            break;
                        escogiendo();
                        if (unavailable())
                            break;
                        if (quiereEnvoltura()) {
                            envoltura();
                        }
                        if (unavailable())
                            break;
                        pagar();
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            if (unavailable())
                break;
        }
    }
}
