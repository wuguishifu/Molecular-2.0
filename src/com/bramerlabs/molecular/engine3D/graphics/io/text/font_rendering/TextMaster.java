package com.bramerlabs.molecular.engine3D.graphics.io.text.font_rendering;

import com.bramerlabs.molecular.engine3D.graphics.io.text.font_mesh_creator.FontType;
import com.bramerlabs.molecular.engine3D.graphics.io.text.font_mesh_creator.GUIText;
import com.bramerlabs.molecular.engine3D.graphics.io.text.font_mesh_creator.TextMeshData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMaster {

    private static Map<FontType, List<GUIText>> texts = new HashMap<>();
    private static FontRenderer renderer;

    private static Loader loader;

    public static void init(Loader theLoader){
        renderer = new FontRenderer();
        loader = theLoader;
    }

    public static void render(){
        renderer.render(texts);
    }

    public static void loadText(GUIText text){
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = texts.computeIfAbsent(font, k -> new ArrayList<>());
        textBatch.add(text);
    }

    public static void removeText(GUIText text){
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if(textBatch.isEmpty()){
            FontType f = (FontType) texts.get(text.getFont());
            texts.remove(f);
        }
    }

    public static void cleanUp(){
        renderer.cleanUp();
    }

}