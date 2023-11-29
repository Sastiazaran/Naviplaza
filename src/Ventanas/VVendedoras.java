package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VVendedoras extends JPanel {
    private List<Point> points;
    private List<Image> vendedoraImages;

    public VVendedoras() {
        this.points = new ArrayList<>();
        this.vendedoraImages = new ArrayList<>();
        setPreferredSize(new Dimension(300, 300));
    }

    public void movePoints(int numberOfVendedoras) {
        Random random = new Random();
        points.clear();

        for (int i = 0; i < Principal.getNumberV(); i++) {
            int x = random.nextInt(300);
            int y = random.nextInt(300);
            points.add(new Point(x, y));
        }
    }

    public void setVendImages(List<ImageIcon> images) {
        vendedoraImages.clear();
        for (ImageIcon icon : images) {
            Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            vendedoraImages.add(image);
        }
    }

    public void startMoving(int numberOfVendedoras) {
        Timer timer = new Timer(1000, e -> {
            movePoints(numberOfVendedoras);
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < points.size(); i++) {
            if (i < vendedoraImages.size()) {
                Image image = vendedoraImages.get(i);
                Point position = points.get(i);
                g.drawImage(image, position.x, position.y, this);
            }

        }
    }
}
