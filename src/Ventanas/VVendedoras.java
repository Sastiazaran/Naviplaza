package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class VVendedoras extends JPanel {

    private ImageIcon imagen;
    private int x, y;

    public VVendedoras(ImageIcon imagen) {
        this.imagen = imagen;
        this.x = getRandomCoordinate();
        this.y = getRandomCoordinate();
    }

    private int getRandomCoordinate() {
        return new Random().nextInt(300);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagen.paintIcon(this, g, x, y);
    }

    public void move() {
        x = getRandomCoordinate();
        y = getRandomCoordinate();
        repaint(); 
    }
}
