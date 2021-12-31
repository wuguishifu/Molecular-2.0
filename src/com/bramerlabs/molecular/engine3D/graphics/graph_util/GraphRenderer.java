package com.bramerlabs.molecular.engine3D.graphics.graph_util;

import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphRenderer {

    private final ArrayList<Point> points;
    private final Dimension displaySize;
    private final ArrayList<Axis> axes;
    private float graphWidth = 0, graphHeight = 0;
    private int graphPaddingX = 0, graphPaddingY = 0;
    private float x1 = 0, y1 = 0, x2 = 0, y2 = 0;

    public GraphRenderer(GraphDisplay gd) {
        this.points = new ArrayList<>();
        displaySize = gd.panel.getPreferredSize();
        axes = new ArrayList<>();
    }

    public void paint(Graphics g) {
        for (Point point : points) {
            paintComponent(g, point.value);
        }
        for (int i = 0; i < points.size() - 1; i++) {
            paintComponent(g, points.get(i).value, points.get(i + 1).value);
        }
        for (Axis axis : axes) {
            axis.paint(g);
        }
    }

    public void paintComponent(Graphics g, Vector2f v) {
        float px = v.x - x1;
        float py = v.y - y1;
        float diffX = px/graphWidth;
        float diffY = py/graphHeight;
        int x = (int) (diffX * (displaySize.width - 2 * graphPaddingX));
        int y = (int) (diffY * (displaySize.height - 2 * graphPaddingY));
        int r = 5;
        g.drawOval(x + graphPaddingX - r, y + graphPaddingY - r, 2 * r, 2 * r);
    }

    public void paintComponent(Graphics g, Vector2f v1, Vector2f v2) {
        float px1 = v1.x - x1;
        float py1 = v1.y - y1;
        float px2 = v2.x - x1;
        float py2 = v2.y - y1;
        float diffX1 = px1/graphWidth;
        float diffY1 = py1/graphHeight;
        float diffX2 = px2/graphWidth;
        float diffY2 = py2/graphHeight;
        int x1 = (int) (diffX1 * (displaySize.width - 2 * graphPaddingX));
        int y1 = (int) (diffY1 * (displaySize.height - 2 * graphPaddingY));
        int x2 = (int) (diffX2 * (displaySize.width - 2 * graphPaddingX));
        int y2 = (int) (diffY2 * (displaySize.height - 2 * graphPaddingY));
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1 + graphPaddingX, y1 + graphPaddingY, x2 + graphPaddingX, y2 + graphPaddingY);
        g2d.dispose();
    }

    public ArrayList<Point> getComponents() {
        return this.points;
    }

    public void addComponent(Point p) {
        this.points.add(p);
    }

    public void addComponent(Vector2f v) {
        this.points.add(new Point(v));
    }

    public void remove(Point p) {
        this.points.remove(p);
    }

    public void addAxis(float v0, float v1, int intervals, String label, int orientation) {
        Font h = new Font("Helvetica", Font.PLAIN, 15);
        Font h1 = new Font("Helvetica", Font.PLAIN, 12);
        axes.add(g -> {
            int paddingX = displaySize.width/10;
            int paddingY = displaySize.height/10;
            graphPaddingX = paddingX;
            graphPaddingY = paddingY;
            if (orientation == X) {
                graphWidth = v1 - v0;
                x1 = v0;
                x2 = v1;
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
                graphHeight = v1 - v0;
                y1 = v0;
                y2 = v1;
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

    public static class Point {

        public Vector2f value;

        public Point(Vector2f value) {
            this.value = value;
        }
    }

}
