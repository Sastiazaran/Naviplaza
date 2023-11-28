package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VSanta extends JPanel {
private List<Point> points;

    public VSanta() {
        this.points = new ArrayList<>();
        setPreferredSize(new Dimension(300, 300));
        movePoints();  // Initialize points
        startMoving(); // Start moving points
    }

    private void movePoints() {
        Random random = new Random();
        points.clear();

        for (int i = 0; i < Principal.getNumberS(); i++) {
            int x = random.nextInt(200);
            int y = random.nextInt(200);
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
        g.setColor(Color.RED);

        for (Point point : points) {
            g.fillOval(point.x, point.y, 10, 10);
        }
    }

}
