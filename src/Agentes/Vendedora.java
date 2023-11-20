package Agentes;

import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;

public class Vendedora extends Agentes {

    public Vendedora(int MAXWIDTH, int MAXHEIGHT, Semaphore[] sem) {
        super(MAXWIDTH, MAXHEIGHT, sem, "vendedora");
        setEstado(Estados.ESPERANDOCLIENTE);
        t = 5000;
        img = new ImageIcon("Imagenes/image1.png");
    }
    
    private boolean envolver;
    private boolean descansar;

    public void esperarCliente() throws InterruptedException {
        System.out.println("Vendedora esperando cliente");
        Thread.sleep(t);
        if (descansar) {
            setEstado(Estados.DESCANSANDO);
            System.out.println("Vendedora descansando");
            Thread.sleep(t);
        }
    }

    public void mostrarProducto() throws InterruptedException {
        setEstado(Estados.MOSTRANDO);
        setSecCrit("Con Cliente");
        System.out.println("Vendedora mostrando producto");
        Thread.sleep(t); 
        //estado = Estados.DESPIDIENDOSE; falta condicional de si se va el cliente
    }

    public void decidirDescansar(){
        int randomDesc = r.nextInt(100);
        descansar = randomDesc % 2 == 0;
        System.out.println("Número aleatorio para descansar: " + randomDesc);
    }

    public void cobrar() {
        setEstado(Estados.COBRANDO);
        setBuffer("Caja Registradora");
        System.out.println("Vendedora cobrando");
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