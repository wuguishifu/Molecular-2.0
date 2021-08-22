package com.bramerlabs.molecular.engine3D.graphics.io.file_util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileLoader {

    public static String load(String pathToFile) {
        StringBuilder fileAsString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(FileLoader.class.getModule().getResourceAsStream(pathToFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileAsString.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not find file at " + pathToFile);
        }
        return fileAsString.toString();
    }

}
