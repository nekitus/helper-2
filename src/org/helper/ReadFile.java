package org.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class ReadFile {

    public static Stack read(String filePath) {

        BufferedReader br = null;
        Stack<String> result = new Stack();


        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(filePath));

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                result.push(sCurrentLine);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
}