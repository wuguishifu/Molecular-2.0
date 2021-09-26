package com.bramerlabs.molecular.ui;

public class GuiObject {

    private GuiObject.OnClickListener onClickListener;

    public void setOnClickListener(GuiObject.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void performClick(int buttonCode) {
        onClickListener.onClick(buttonCode);
    }

    public interface OnClickListener {
        boolean onClick(int buttonCode);
    }
}
