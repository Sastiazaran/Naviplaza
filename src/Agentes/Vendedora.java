package Agentes;

import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Vendedora implements Runnable {
    Estados estado;
    ImageIcon img;
    Random r;
    int x;
    int y;
    boolean dead;
    Semaphore s;
    int t;

    public Vendedora(int MAXWIDTH, int MAXHEIGHT, Semaphore sem) {
        estado = Estados.ESPERANDO;
        r = new Random();
        x = r.nextInt(MAXWIDTH);
        y = r.nextInt(MAXHEIGHT);
        dead = false;
        s = sem;
        t = 5000;
        img = new ImageIcon("Imagenes/image1.png");
    }
    
    private static final Random random = new Random();
    private boolean envolver;
    private boolean descansar;

    public void esperarCliente() throws InterruptedException {
        System.out.println("Vendedora esperando cliente");
        Thread.sleep(t);
        if (descansar) {
            estado = Estados.DESCANSANDO;
            System.out.println("Vendedora descansando");
            Thread.sleep(t);
        }
    }

    public void mostrarProducto() throws InterruptedException {
        estado = Estados.VENDIENDO;
        System.out.println("Vendedora mostrando producto");
        Thread.sleep(t); 
        //estado = Estados.DESPIDIENDOSE; falta condicional de si se va el cliente
    }

    public void decidirDescansar(){
        int randomDesc = random.nextInt(100);
        descansar = randomDesc % 2 == 0;
        System.out.println("Número aleatorio para descansar: " + randomDesc);
    }

    public void cobrar() {
        estado = Estados.COBRANDO;
        System.out.println("Vendedora cobrando");
    }

    public void decidirEnvoltura() {
        int randomEnv = random.nextInt(100);
        envolver = randomEnv % 2 == 0;
        System.out.println("Número aleatorio para envolver: " + randomEnv);
    }

    public void envolverYEntregar() throws InterruptedException {
        if (envolver) {
            estado = Estados.ENVOLVIENDO;
            System.out.println("Vendedora envolviendo producto");
            Thread.sleep(t); 
        }
        System.out.println("Vendedora entregando producto");
        estado = Estados.DESPIDIENDOSE;
    }

    @Override
    public void run() {
        while (!dead) {
            try { 
                esperarCliente();
                decidirDescansar();
                mostrarProducto();
                cobrar();
                decidirEnvoltura();
                envolverYEntregar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}