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
        gr.addAxis(0, 10, 5, "velocity [cm/s]", GraphRenderer.X);
        gr.addAxis(-5, 5, 5, "pressure drop [Pa]", GraphRenderer.Y);
        for (int i = 0; i < 100; i++) {
            gr.addComponent(new Vector2f(i/10.f, 3 * equation(i/10f)));
        }
        gd.repaint();
    }

    public static float equation(float x) {
        return (float) (Math.sin(x) * Math.cos(2 * x));
    }

}