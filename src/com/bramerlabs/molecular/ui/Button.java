package com.bramerlabs.molecular.ui;

public class Button {

    public float x, y;
    public float w, h;

    public Button.OnClickListener onClickListener;

    public Button(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        // default on click
        this.setOnClickListener(buttonCode -> false);
    }

    public boolean inBounds(float x, float y) {
        return (x < this.x + w && x > this.x && y < this.y + h && y > this.y);
    }

    public void setOnClickListener(Button.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void performClick(int buttonCode) {
        onClickListener.onClick(buttonCode);
    }

    public interface OnClickListener {
        boolean onClick(int buttonCode);
    }

}
