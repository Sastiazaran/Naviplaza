package Agentes;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Vendedora extends Agentes {
    private Semaphore venderS;
    private Semaphore descansS;
    private boolean clienteEsperando;

    public Vendedora(int MAXWIDTH, int MAXHEIGHT, Semaphore descS, Semaphore vendS, int t) {
        super(MAXWIDTH, MAXHEIGHT, "vendedora");
        setEstado(Estados.ESPERANDOCLIENTE);
        this.t = t;
        venderS = vendS;
        descansS = descS;
        clienteEsperando = false;
        img = new ImageIcon("Imagenes/image1.png");
    }

    private boolean envolver;
    private boolean descansar;

    public void esperarCliente() throws InterruptedException {
        System.out.println("Vendedora esperando cliente");
        setEstado(Estados.ESPERANDOCLIENTE);
        if (descansar) {
            setEstado(Estados.DESCANSANDO);
            setBuffer("Coffee Break");
            System.out.println("Vendedora descansando");
            Thread.sleep(t);
            setBuffer("none");
        }
        Thread.sleep(t);
    }

    public void mostrarProducto() throws InterruptedException {
        setEstado(Estados.MOSTRANDO);
        setSecCrit("Con Cliente");
        System.out.println("Vendedora mostrando producto");
        Thread.sleep(t);
        // estado = Estados.DESPIDIENDOSE; falta condicional de si se va el cliente
    }

    public void decidirDescansar() {
        int randomDesc = r.nextInt(100);
        descansar = randomDesc % 2 == 0;
        System.out.println("Número aleatorio para descansar: " + randomDesc);
    }

    public void cobrar() throws InterruptedException {
        setEstado(Estados.COBRANDO);
        setBuffer("Caja Registradora");
        System.out.println("Vendedora cobrando");
        Thread.sleep(t);
    }

    public void decidirEnvoltura() {
        int randomEnv = r.nextInt(100);
        envolver = randomEnv % 2 == 0;
        System.out.println("Número aleatorio para envolver: " + randomEnv);
    }

    public void envolverYEntregar() throws InterruptedException {
        if (envolver) {
            setEstado(Estados.ENVOLVIENDO);
            System.out.println("Vendedora envolviendo producto");
            Thread.sleep(t);
        }
        System.out.println("Vendedora entregando producto");
        setEstado(Estados.DESPIDIENDOSE);
        setBuffer("none");
        setSecCrit("none");
        Thread.sleep(t / 2);
    }

    @Override
    public void run() {
        while (!unavailable()) {
            try {
                esperarCliente();
                if (unavailable())
                    break;
                descansS.acquire();
                decidirDescansar();
                descansS.release();
                if (unavailable())
                    break;
                try {
                    venderS.acquire();
                    mostrarProducto();
                    if (unavailable())
                        break;
                    cobrar();
                    decidirEnvoltura();
                    if (unavailable())
                        break;
                    envolverYEntregar();
                    if (unavailable())
                        break;
                } catch (Exception e) {
                }
                venderS.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cliente(String name) {
        clienteEsperando = true;
    }
}