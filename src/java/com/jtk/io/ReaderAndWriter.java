package com.jtk.io;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by jubin on 7/8/2017.
 */
public class ReaderAndWriter {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        System.out.println("Charset.availableCharsets() = " + Charset.availableCharsets());
        try(PrintWriter printWriter = new PrintWriter("korea.txt", "EUC-KR");
            PrintWriter malayalamWriter = new PrintWriter("malayalam.txt","UTF-8")){
            printWriter.write("한국어 키보드");
            printWriter.flush();
            malayalamWriter.write("മുന്നറിയിപ്പ്...");
            malayalamWriter.flush();

        }finally {
        }

    }
}
