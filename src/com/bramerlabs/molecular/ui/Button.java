package com.bramerlabs.molecular.ui;

public class Button {

    public int x, y;
    public int w, h;

    public Button(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean inBounds(int x, int y) {
        return (x < this.x + w && x > this.x && y < this.y + h && y > this.y);
    }

}
