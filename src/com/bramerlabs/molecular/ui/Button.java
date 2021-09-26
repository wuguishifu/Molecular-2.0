package com.bramerlabs.molecular.ui;

public class Button extends GuiObject {

    public float x, y;
    public float w, h;

    public Button(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean inBounds(float x, float y) {
        return (x < this.x + w && x > this.x && y < this.y + h && y > this.y);
    }

}
