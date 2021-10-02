package com.bramerlabs.molecular.engine3D.gui;

import com.bramerlabs.molecular.engine3D.graphics.io.file_util.FileLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class GuiMaterial {

    private String pathToTexture;
    private static final String FORMAT = "PNG";

    private float width, height;

    private int textureID;
    private Texture texture;

    public GuiMaterial(String pathToTexture) {
        this.pathToTexture = pathToTexture;
        this.create();
    }

    public void create() {
        try {
            texture = TextureLoader.getTexture(FORMAT, FileLoader.class.getModule().getResourceAsStream(pathToTexture), GL11.GL_NEAREST);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load texture.");
        }

        width = texture.getWidth();
        height = texture.getHeight();

        textureID = texture.getTextureID();
    }

    public void destroy() {
        GL20.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public int getTextureID() {
        return textureID;
    }

}
