package Agentes;

import java.util.concurrent.Semaphore;

import util.Resources;

public class Santa extends Agentes {
    private final Semaphore santaS;
    private boolean clienteEsperando;

    public Santa(int maxWidth, int maxHeight, Semaphore semSanta, int t) {
        super(maxWidth, maxHeight, "santa");
        this.t = t;
        santaS = semSanta;
        clienteEsperando = false;
        img = Resources.icon("image3.png");
        setEstado(Estados.DESCANSANDO);
    }

    @Override
    public void setEstado(Estados estado) {
        super.setEstado(estado);
        switch (estado) {
            case POSANDO:
                img = Resources.icon("StateImages/Santa/santa_posando.png");
                break;
            case PLATICANDO:
                img = Resources.icon("StateImages/Santa/santa_platicando.png");
                break;
            case SALUDANDO:
                img = Resources.icon("StateImages/Santa/santa_saludando2.jpg");
                break;
            default:
                img = Resources.icon("image3.png");
                break;
        }
    }

    private void saludando() {
        setEstado(Estados.SALUDANDO);
        sleep();
    }

    private void platicando() {
        setEstado(Estados.PLATICANDO);
        sleep();
    }

    private void posando() {
        setEstado(Estados.POSANDO);
        sleep();
    }

    private void despidiendose() {
        setEstado(Estados.DESPIDIENDOSE);
        sleep();
    }

    private void descansando() {
        setEstado(Estados.DESCANSANDO);
        sleep();
    }

    private int decidirQueHacer() {
        return r.nextInt(2);
    }

    @Override
    public void run() {
        while (!unavailable() && !Thread.currentThread().isInterrupted()) {
            descansando();
            if (unavailable()) {
                break;
            }
            if (decidirQueHacer() != 0) {
                continue;
            }
            boolean acquired = false;
            try {
                santaS.acquire();
                acquired = true;
                saludando();
                if (unavailable()) {
                    continue;
                }
                platicando();
                if (unavailable()) {
                    continue;
                }
                posando();
                if (unavailable()) {
                    continue;
                }
                despidiendose();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                if (acquired) {
                    santaS.release();
                }
            }
        }
    }

    public void cliente(String name) {
        clienteEsperando = true;
    }
}
