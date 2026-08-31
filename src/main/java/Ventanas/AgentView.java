package Ventanas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import Agentes.Agentes;

abstract class AgentView extends JPanel {
    static final int SPRITE_SIZE = 48;
    private final Agentes[] agents;
    private final Timer timer;

    AgentView(Agentes[] agents) {
        this.agents = agents == null ? new Agentes[0] : agents;
        setPreferredSize(new Dimension(500, 500));
        timer = new Timer(50, e -> {
            int width = getWidth();
            int height = getHeight();
            for (Agentes agent : this.agents) {
                if (agent != null) {
                    agent.wander(width, height, SPRITE_SIZE);
                }
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Agentes agent : agents) {
            if (agent == null) {
                continue;
            }
            ImageIcon sprite = agent.getSprite();
            if (sprite == null) {
                continue;
            }
            Image image = sprite.getImage();
            g.drawImage(image, agent.getX(), agent.getY(), SPRITE_SIZE, SPRITE_SIZE, this);
        }
    }
}
