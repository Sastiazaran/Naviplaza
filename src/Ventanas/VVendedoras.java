package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VVendedoras extends JPanel {
    private List<Point> points;
    private List<ImageIcon> vendedoraImages;

    public VVendedoras(ArrayList<ImageIcon> vendedoraImages) {
        this.points = new ArrayList<>();
        this.vendedoraImages = vendedoraImages;
        setPreferredSize(new Dimension(300, 300));
        movePoints();  // Initialize points
        startMoving(); // Start moving points
    }

    private void movePoints() {
        Random random = new Random();
        points.clear();

        for (int i = 0; i < Principal.getNumberV(); i++) {
            int x = random.nextInt(1200);
            int y = random.nextInt(300);
            points.add(new Point(x, y));
        }
    }

    private void startMoving() {
        Timer timer = new Timer(1000, e -> {
            movePoints();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            Image vendedoraImage = vendedoraImages.get(0).getImage();
            g.drawImage(vendedoraImage, point.x, point.y, this);
        }
    }
}
