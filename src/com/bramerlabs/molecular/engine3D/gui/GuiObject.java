package com.bramerlabs.molecular.engine3D.gui;

import com.bramerlabs.molecular.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector2f;
import com.bramerlabs.molecular.engine3D.math.vector.Vector3f;

public class GuiObject {

    public GuiMesh mesh;
    public GuiSelectionBox selectionBox;

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Matrix4f model;

    private GuiObject.OnClickListener onClickListener;

    public GuiObject(GuiMesh mesh, GuiSelectionBox selectionBox, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.mesh = mesh;
        this.selectionBox = selectionBox;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        this.model = Matrix4f.transform(position, rotation, scale);

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
