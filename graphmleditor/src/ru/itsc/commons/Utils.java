/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author koryakov.rv
 */
public class Utils {
    private static final String os = System.getProperty("os.name").toLowerCase(new Locale(""));

    /**
     * Returns true if the operating system is a form of Windows.
     * @return 
     */
    public static boolean isWindows(){
        return os.contains("win");
    }

    /**
     * Returns true if the operating system is a form of Mac OS.
     * @return 
     */
    public static boolean isMac(){
        return os.contains("mac");
    }

    public static boolean isCorrectUrl(String url) {
        boolean result = true;
        try {
            URL ur = new URL(url);
        } catch (MalformedURLException ex) {
            result = false;
            Logger.getLogger(Utils.class.toString()).log(Level.SEVERE, ex.getMessage());
        }
        
        return result;
    }
    
    
    /**
     * Load a text file into a String
     *
     * @param url The url to load file from
     * @return file contents as a string
     */
    public static String loadFile(String url) {
        try {
            return loadFile(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Load a text file into a String
     *
     * @param url The url to load file from
     * @return file contents as a string
     */
    public static String loadFile(URL url) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Copy a file from a URL to a destination file
     *
     * @param url The url to copy file from
     * @param destFilePath The destination File
     */
    public static void copyFile(URL url, String destFilePath) {
        byte[] buffer = new byte[4096];
        // File destFile = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            File destFile = new File(destFilePath);
            fileOutputStream = new FileOutputStream(destFile);
            inputStream = url.openStream();
            int bytesRead = inputStream.read(buffer);
            while (bytesRead != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                bytesRead = inputStream.read(buffer);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
}
