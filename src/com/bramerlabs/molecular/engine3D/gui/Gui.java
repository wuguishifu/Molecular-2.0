package com.bramerlabs.molecular.engine3D.gui;

import java.util.ArrayList;

public class Gui {

    public ArrayList<GuiObject> guiObjects;

    public Gui(ArrayList<GuiObject> guiObjects) {
        this.guiObjects = guiObjects;
    }

    public void update(float mouseX, float mouseY, int buttonCode) {
        for (GuiObject object : guiObjects) {
            if (object.inBounds(mouseX, mouseY)) {
                object.performClick(buttonCode);
            }
        }
    }

}
