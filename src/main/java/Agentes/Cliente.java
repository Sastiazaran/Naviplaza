package Agentes;

import java.util.concurrent.Semaphore;

import util.Resources;

public class Cliente extends Agentes {
    private final Semaphore santaS;
    private final Semaphore comprarS;

    public Cliente(int maxWidth, int maxHeight, Semaphore santaSem, Semaphore comprarSem, int t, Vendedora[] v,
            Santa[] s) {
        super(maxWidth, maxHeight, "cliente");
        this.t = t;
        santaS = santaSem;
        comprarS = comprarSem;
        img = Resources.icon("image2.png");
        setEstado(Estados.PASEANDO);
    }

    @Override
    public void setEstado(Estados estado) {
        super.setEstado(estado);
        if (estado == Estados.PASEANDO) {
            img = Resources.icon("StateImages/Cliente/cliente_paseando.jpg");
        } else {
            img = Resources.icon("image2.png");
        }
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
        return r.nextInt(2) == 0;
    }

    private void escogiendo() {
        setEstado(Estados.ESCOGIENDOREG);
        setBuffer("none");
        setSecCrit("Con Vendedora");
        sleep();
    }

    private boolean quiereEnvoltura() {
        return r.nextInt(2) == 0;
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
        while (!unavailable() && !Thread.currentThread().isInterrupted()) {
            paseando();
            if (unavailable()) {
                break;
            }
            int d = decidirQueHacer();
            if (unavailable()) {
                break;
            }
            switch (d) {
                case 0:
                    visitSanta();
                    break;
                case 1:
                    visitShop();
                    break;
                default:
                    break;
            }
        }
    }

    private void visitSanta() {
        boolean acquired = false;
        try {
            santaS.acquire();
            acquired = true;
            esperarSanta();
            if (!unavailable()) {
                conSanta();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) {
                santaS.release();
            }
        }
    }

    private void visitShop() {
        boolean acquired = false;
        try {
            comprarS.acquire();
            acquired = true;
            if (verRegalos()) {
                if (unavailable()) {
                    return;
                }
                escogiendo();
                if (unavailable()) {
                    return;
                }
                if (quiereEnvoltura()) {
                    envoltura();
                }
                if (unavailable()) {
                    return;
                }
                pagar();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) {
                comprarS.release();
            }
        }
    }
}
