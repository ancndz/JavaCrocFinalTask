package ru.ancndz.utils;

import java.io.*;

public class XMLSaveUtil {

    private static final String pathToFiles = "src/main/resources/xmlFiles/";

    private static void createPath() {
        try {
            File dir = new File(pathToFiles);
            dir.mkdirs();
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void clearDir() throws IOException {
        File workDir = new File(pathToFiles);
        File[] entries = workDir.listFiles();
        if (entries == null) {
            return;
        }
        for (File eachFile: entries) {
            if (!eachFile.isDirectory()) {
                if (!eachFile.delete()) {
                    throw new IOException("Failed to delete " + eachFile);
                }
            }
        }
    }

    public static void save(String filename, String xmlString) throws IOException {
        createPath();
        clearDir();
        String path = pathToFiles + filename + ".xml";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(xmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String load() {
        File workDir = new File(pathToFiles);
        File[] entries = workDir.listFiles();
        if (entries == null) {
            return null;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(entries[0]))) {
            StringBuilder xmlString = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                xmlString.append(line);
            }
            return xmlString.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
