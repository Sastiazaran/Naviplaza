package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VClientes extends JPanel {

   private List<Point> points;

    public VClientes() {
        this.points = new ArrayList<>();
        setPreferredSize(new Dimension(1200, 300));
        movePoints();  // Initialize points
        startMoving(); // Start moving points
    }

    private void movePoints() {
        Random random = new Random();
        points.clear();

        for (int i = 0; i < Principal.getNumberC(); i++) {
            int x = random.nextInt(300);
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
        g.setColor(Color.GRAY);

        for (Point point : points) {
            g.fillOval(point.x, point.y, 10, 10);
        }
    }

}
