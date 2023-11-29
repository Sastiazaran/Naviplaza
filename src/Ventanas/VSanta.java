package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VSanta extends JPanel {

    private List<Point> points;
    private List<Image> santaImages;

    public VSanta() {

        this.points = new ArrayList<>();
        this.santaImages = new ArrayList<>();
        setPreferredSize(new Dimension(300, 300));

    }

    public void movePoints(int numberOfSantas) {
        Random random = new Random();
        points.clear();

        for (int i = 0; i < Principal.getNumberS(); i++) {
            int x = random.nextInt(200);
            int y = random.nextInt(200);
            points.add(new Point(x, y));
        }
    }

    public void setSantaImages(List<ImageIcon> images) {
        santaImages.clear();
        for (ImageIcon icon : images) {
            Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            santaImages.add(image);
        }

    }

    public void startMoving(int numberOfSantas) {
        Timer timer = new Timer(1000, e -> {
            movePoints(numberOfSantas);
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar las imágenes en lugar de los puntos
        for (int i = 0; i < points.size(); i++) {
            if (i < santaImages.size()) {
                Image image = santaImages.get(i);
                Point position = points.get(i);
                g.drawImage(image, position.x, position.y, this);
            }
        }
    }

}
