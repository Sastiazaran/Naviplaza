package Ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VClientes extends JPanel {

   private List<Point> points;
   private List<Image> clientImages;

    public VClientes() {
        this.points = new ArrayList<>();
        this.clientImages = new ArrayList<>();
        setPreferredSize(new Dimension(300, 300));
    }

    public void movePoints(int numberOfClients) {
        Random random = new Random();
        points.clear();

        for (int i = 0; i < Principal.getNumberC(); i++) {
            int x = random.nextInt(200);
            int y = random.nextInt(200);
            points.add(new Point(x, y));
        }
    }

    public void setClientImages(List<ImageIcon> images){
        clientImages.clear();
        for(ImageIcon icon : images){
            Image image = icon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
            clientImages.add(image);
        }
    }

    public void startMoving(int numberOfClients) {
        Timer timer = new Timer(1000, e -> {
            movePoints(numberOfClients);
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < points.size(); i++) {
            if(i < clientImages.size()){
                Image image = clientImages.get(i);
                Point position = points.get(i);
                g.drawImage(image, position.x, position.y, this);
            }
            
        }
    }

}
