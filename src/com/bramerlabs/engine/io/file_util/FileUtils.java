package com.bramerlabs.engine.io.file_util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    /**
     * loads a file as a string
     * @param pathToFile - the path to the file
     * @return - the loaded file as a string
     */
    public static String loadAsString(String pathToFile) {

        // create a new string builder
        StringBuilder fileAsString = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getModule().getResourceAsStream(pathToFile)))) {

            // read each line in the file
            String line;
            while ((line = reader.readLine()) != null) {

                // add the line to the result string
                fileAsString.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not find file at " + pathToFile);
        }

        // return the loaded file
        return fileAsString.toString();
    }

}
