package com.bramerlabs.molecular.engine3D.gui;

public class GuiObject {

    public GuiMesh mesh;
    public GuiSelectionBox selectionBox;

    private GuiObject.OnClickListener onClickListener;

    public GuiObject(GuiMesh mesh, GuiSelectionBox selectionBox) {
        this.mesh = mesh;
        this.selectionBox = selectionBox;
        this.onClickListener = buttonCode -> false;
    }

    public boolean performClick(int buttonCode) {
        return this.onClickListener.onClick(buttonCode);
    }

    public void setOnClickListener(GuiObject.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public GuiObject.OnClickListener removeOnClickListener() {
        GuiObject.OnClickListener listener = this.onClickListener;
        this.onClickListener = buttonCode -> false;
        return listener;
    }

    public boolean inBounds(float mouseX, float mouseY) {
        return this.selectionBox.inBounds(mouseX, mouseY);
    }

    public interface OnClickListener {
        boolean onClick(int buttonCode);
    }

}
