package com.bramerlabs.molecular.engine3D.graphics.graph_util;

import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphDisplay {

    public JFrame frame;
    public JPanel panel;

    public ArrayList<GraphRenderer> renderers;

    public GraphDisplay(Dimension windowSize) {
        renderers = new ArrayList<>();
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                for (GraphRenderer renderer : renderers) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    super.paint(g);

                    renderer.paint(g);
                }
            }
        };
        panel.setPreferredSize(windowSize);
        frame.add(panel);
        frame.pack();

        frame.setVisible(true);
    }

    public void repaint() {
        panel.repaint();
    }

    public void addRenderer(GraphRenderer gr) {
        this.renderers.add(gr);
    }

    public static void main(String[] args) {
        GraphDisplay gd = new GraphDisplay(new Dimension(800, 600));
        GraphRenderer gr = new GraphRenderer(gd);
        gd.addRenderer(gr);
        gr.addAxis(0, 10, 5, "velocity [cm/s]", GraphRenderer.X, true);
        gr.addAxis(-5, 5, 5, "pressure drop [Pa]", GraphRenderer.Y, true);
        for (int i = 0; i < 100; i++) {
            gr.addComponent(new Vector2f(i/10f, i/10f));
        }
        gd.repaint();
    }
}
