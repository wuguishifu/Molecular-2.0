package com.bramerlabs.molecular.ui;

public class Gui {

    public Button[] buttons;

    public Gui(Button[] buttons) {
        this.buttons = buttons;
    }

    public void update(float mouseX, float mouseY, int buttonCode) {
        for (Button button : buttons) {
            if (button.inBounds(mouseX, mouseY)) {
                button.performClick(buttonCode);
            }
        }
    }

}
