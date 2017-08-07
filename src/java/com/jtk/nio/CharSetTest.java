package com.jtk.nio;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by jubin on 7/8/2017.
 */
public class CharSetTest {
    public static void main(String[] args) {
        String msg = "façade touché";
        String[] csNames = {"US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16"};
        for (String csName : csNames) {
            //Charset forName(String charsetName) factory method is used to obtain the
            // Charset instance that corresponds to charsetName.
            encode(msg, Charset.forName(csName));
        }
    }

    public static void encode(String msg, Charset cs) {
        System.out.println("Charset: " + cs.toString());
        System.out.println("Message: " + msg);
        ByteBuffer buffer = cs.encode(msg);
        System.out.print("Encoded :");

        for (int i = 0; buffer.hasRemaining(); i++) {
            int _byte = buffer.get() & 255;//iterates over the bytes in the byte buffer, converting each byte to a character.
            char ch = (char) _byte;
            /*if (Character.isWhitespace(ch) || Character.isISOControl(ch))// not pri
                ch = '\u0000';*/
            //System.out.printf("%2d: %02x (%c)", i, _byte, ch);
            System.out.printf("%c", ch);
        }

        System.out.println("\n buffer = " + cs.decode((ByteBuffer) buffer.rewind()).toString());
        System.out.println();
    }
}
