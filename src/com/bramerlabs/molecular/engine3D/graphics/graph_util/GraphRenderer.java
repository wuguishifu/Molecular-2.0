package com.bramerlabs.molecular.engine3D.graphics.graph_util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphRenderer {

    private final ArrayList<GraphComponent> components;
    private final GraphDisplay gd;
    private final Dimension displaySize;
    private final ArrayList<Axis> axes;

    public GraphRenderer(GraphDisplay gd) {
        this.components = new ArrayList<>();
        this.gd = gd;
        displaySize = gd.panel.getPreferredSize();
        axes = new ArrayList<>();
    }

    public void paint(Graphics g) {
        for (GraphComponent component : components) {
            component.paint(g);
        }
        for (Axis axis : axes) {
            axis.paint(g);
        }
    }

    public ArrayList<GraphComponent> getComponents() {
        return this.components;
    }

    public void addComponent(GraphComponent g) {
        this.components.add(g);
    }

    public void remove(GraphComponent g) {
        this.components.remove(g);
    }

    public void addAxis(float v0, float v1, int intervals, String label, int orientation) {
        Font h = new Font("Helvetica", Font.PLAIN, 15);
        Font h1 = new Font("Helvetica", Font.PLAIN, 12);
        DecimalFormat df2 = new DecimalFormat("#,###,###,#00.00");
        axes.add(g -> {
            int paddingX = displaySize.width/10;
            int paddingY = displaySize.height/10;
            if (orientation == X) {
                g.drawLine(paddingX, displaySize.height - paddingY,
                        displaySize.width - paddingX, displaySize.height - paddingY);
                drawCenteredString(g, label,
                        new Rectangle(0, displaySize.height - paddingY/2,displaySize.width, paddingY/2), h, 0);
                // draw intervals
                for (int i = 0; i < intervals; i++) {
                    int dx = (displaySize.width - 2 * paddingX) / (intervals - 1);
                    int x = paddingX + i * dx;
                    int y1 = displaySize.height - paddingY;
                    int y2 = displaySize.height - 5 * paddingY / 6;
                    g.drawLine(x, y1, x, y2);
                    float intervalValue = (v1 - v0) / (intervals - 1);
                    float value = i * intervalValue + v0;
                    FontMetrics metrics = g.getFontMetrics(h1);
                    int yPos = y1 + metrics.getHeight()/2 + metrics.getAscent();
                    g.setFont(h1);
                    g.drawString(String.format("%6.3e", value), x - paddingX/6, yPos);
                }
            } else if (orientation == Y) {
                g.drawLine(paddingX, paddingY, paddingX, displaySize.height - paddingY);
                drawCenteredString(g, label,
                        new Rectangle(0, 0, paddingX/2, displaySize.height), h, 90);
                // draw intervals
                for (int i = 0; i < intervals; i++) {
                    int dy = (displaySize.height - 2 * paddingY) / (intervals - 1);
                    int y = paddingY + i * dy;
                    int x1 = 5 * paddingX / 6;
                    g.drawLine(x1, y, paddingX, y);
                    float intervalValue = (v0 - v1) / (intervals - 1);
                    float value = i * intervalValue + v1;
                    FontMetrics metrics = g.getFontMetrics(h1);
                    String text = String.format("%6.2e", value);
                    int xPos = x1 - metrics.getHeight() + metrics.getAscent();
                    AffineTransform affineTransform = new AffineTransform();
                    affineTransform.rotate(Math.toRadians(-90), 0, 0);
                    Font rotatedFont = h1.deriveFont(affineTransform);
                    g.setFont(rotatedFont);
                    g.drawString(text, xPos, y + paddingY / 5);
                }
            }
        });
    }
    public static int X = 0;
    public static int Y = 1;

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, int angle) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        Graphics2D g2d = (Graphics2D) g;
        if (angle == 0) {
            g2d.drawString(text, x, y);
        } else {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(-angle),
                metrics.stringWidth(text)/2., 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            g2d.setFont(rotatedFont);
            g2d.drawString(text, x, y);
        }
    }

    private interface Axis {
        void paint(Graphics g);
    }

}
