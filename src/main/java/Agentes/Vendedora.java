package Agentes;

import java.util.concurrent.Semaphore;

import util.Resources;

public class Vendedora extends Agentes {
    private final Semaphore venderS;
    private final Semaphore descansS;
    private boolean clienteEsperando;
    private boolean envolver;
    private boolean descansar;

    public Vendedora(int maxWidth, int maxHeight, Semaphore descS, Semaphore vendS, int t) {
        super(maxWidth, maxHeight, "vendedora");
        this.t = t;
        venderS = vendS;
        descansS = descS;
        clienteEsperando = false;
        img = Resources.icon("image1.png");
        setEstado(Estados.ESPERANDOCLIENTE);
    }

    @Override
    public void setEstado(Estados estado) {
        super.setEstado(estado);
        switch (estado) {
            case MOSTRANDO:
                img = Resources.icon("StateImages/Vendedora/vendedora_mostrarProducto_Final.png");
                break;
            case COBRANDO:
                img = Resources.icon("StateImages/Vendedora/vendedora_cobrando.png");
                break;
            case ESPERANDOCLIENTE:
                img = Resources.icon("Resized/image1.png");
                break;
            default:
                img = Resources.icon("image1.png");
                break;
        }
    }

    public void esperarCliente() throws InterruptedException {
        setEstado(Estados.ESPERANDOCLIENTE);
        if (descansar) {
            setEstado(Estados.DESCANSANDO);
            setBuffer("Coffee Break");
            Thread.sleep(Math.max(1, t));
            setBuffer("none");
        }
        Thread.sleep(Math.max(1, t));
    }

    public void mostrarProducto() throws InterruptedException {
        setEstado(Estados.MOSTRANDO);
        setSecCrit("Con Cliente");
        Thread.sleep(Math.max(1, t));
    }

    public void decidirDescansar() {
        descansar = r.nextInt(100) % 2 == 0;
    }

    public void cobrar() throws InterruptedException {
        setEstado(Estados.COBRANDO);
        setBuffer("Caja Registradora");
        Thread.sleep(Math.max(1, t));
    }

    public void decidirEnvoltura() {
        envolver = r.nextInt(100) % 2 == 0;
    }

    public void envolverYEntregar() throws InterruptedException {
        if (envolver) {
            setEstado(Estados.ENVOLVIENDO);
            Thread.sleep(Math.max(1, t));
        }
        setEstado(Estados.DESPIDIENDOSE);
        setBuffer("none");
        setSecCrit("none");
        Thread.sleep(Math.max(1, t / 2));
    }

    @Override
    public void run() {
        while (!unavailable() && !Thread.currentThread().isInterrupted()) {
            try {
                esperarCliente();
                if (unavailable()) {
                    break;
                }
                descansS.acquire();
                try {
                    decidirDescansar();
                } finally {
                    descansS.release();
                }
                if (unavailable()) {
                    break;
                }
                venderS.acquire();
                try {
                    mostrarProducto();
                    if (unavailable()) {
                        break;
                    }
                    cobrar();
                    decidirEnvoltura();
                    if (unavailable()) {
                        break;
                    }
                    envolverYEntregar();
                } finally {
                    venderS.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void cliente(String name) {
        clienteEsperando = true;
    }
}
