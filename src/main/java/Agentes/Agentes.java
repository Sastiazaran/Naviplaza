package Agentes;

import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Agentes implements Runnable {
    Estados estado;
    ImageIcon img;
    Random r;
    int x;
    int y;
    int dx;
    int dy;
    boolean dead;
    Agentes interactuaCon;
    int t;
    String name;
    String type;
    String secCrit;
    String buffer;

    public Agentes(int maxWidth, int maxHeight, String t) {
        r = new Random();
        int width = Math.max(1, maxWidth);
        int height = Math.max(1, maxHeight);
        x = r.nextInt(width);
        y = r.nextInt(height);
        dx = r.nextBoolean() ? 3 : -3;
        dy = r.nextBoolean() ? 3 : -3;
        dead = false;
        setBuffer("none");
        setSecCrit("none");
        type = t;
    }

    public void setName(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageIcon getSprite() {
        return img;
    }

    public String getEstado() {
        return estado == null ? "" : estado.name();
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public String getSecCrit() {
        return secCrit;
    }

    public void setSecCrit(String secCrit) {
        this.secCrit = secCrit;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isPanic() {
        return estado == Estados.PANICO;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
        if (dead) {
            setBuffer("none");
            setSecCrit("none");
            setEstado(Estados.MUERTO);
        }
    }

    public String getDeadString() {
        return String.valueOf(dead);
    }

    public void sleep() {
        try {
            Thread.sleep(Math.max(1, t));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean unavailable() {
        return isDead() || isPanic();
    }

    /**
     * Returns a visible marker when this agent is in {@code e}, used by per-type status tables.
     */
    public String getEstado(Estados e) {
        if (estado == e) {
            return "X";
        }
        return " ";
    }

    public void wander(int panelWidth, int panelHeight, int spriteSize) {
        if (unavailable() || panelWidth <= spriteSize || panelHeight <= spriteSize) {
            return;
        }
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = Math.abs(dx);
        }
        if (y < 0) {
            y = 0;
            dy = Math.abs(dy);
        }
        if (x > panelWidth - spriteSize) {
            x = panelWidth - spriteSize;
            dx = -Math.abs(dx);
        }
        if (y > panelHeight - spriteSize) {
            y = panelHeight - spriteSize;
            dy = -Math.abs(dy);
        }
    }
}
