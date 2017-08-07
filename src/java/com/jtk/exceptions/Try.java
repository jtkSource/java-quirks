package com.jtk.exceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by jubin on 6/5/2017.
 */
public class Try {

    public static void main(String[] args) {
        String line;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\jubin\\Downloads\\FL_insurance_sample.csv\\FL_insurance_sample.csv"))){
            while ((line = bufferedReader.readLine())!=null){
                System.out.println(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
