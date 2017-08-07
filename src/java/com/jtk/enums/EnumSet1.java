package com.jtk.enums;

import java.util.EnumSet;

/**
 * Created by jubin on 6/18/2017.
 */
public class EnumSet1 {
    public static final int ADD_SALUTATION_M = 1 << 0;  // 1
    public static final int ADD_SALUTATION_F = 1 << 1;  // 2
    public static final int ADD_CLOSING      = 1 << 2;  // 4
    public static final int ADD_SIGNATURE    = 1 << 3;  // 8

    public static String formatLetter(String msg, int bitValue) {
        if ((bitValue & ADD_SALUTATION_M) == ADD_SALUTATION_M) {
            msg = "Dear Sir, \n" + msg;
        }
        if ((bitValue & ADD_SALUTATION_F) == ADD_SALUTATION_F) {
            msg = "Dear Ma'am, \n  " + msg;
        }
        if ((bitValue & ADD_CLOSING) == ADD_CLOSING) {
            msg = msg + "\nYours Loving";
        }
        if ((bitValue & ADD_SIGNATURE) == ADD_SIGNATURE) {
            msg = msg + "\nJubin";
        }
        return msg;
    }

    public static String formatLetterEnums(String msg, EnumSet<LetterFormat> letterFormats) {
        if (letterFormats.contains(LetterFormat.ADD_SALUTATION_M)) {
            msg = "Dear Sir, \n" + msg;
        }
        if (letterFormats.contains(LetterFormat.ADD_SALUTATION_F)) {
            msg = "Dear Ma'am, \n  " + msg;
        }
        if (letterFormats.contains(LetterFormat.ADD_CLOSING)) {
            msg = msg + "\nYours Loving";
        }
        if (letterFormats.contains(LetterFormat.ADD_SIGNATURE)) {
            msg = msg + "\nJubin";
        }
        return msg;
    }

    public static void main(String[] args) {
        System.out.println("letter = \n" + formatLetter("This is just great!!", ADD_SALUTATION_M | ADD_CLOSING | ADD_SIGNATURE));
        System.out.println("++++++++++++++++++++++++++++++++");
        EnumSet<LetterFormat> formats = EnumSet.of(LetterFormat.ADD_SALUTATION_M, LetterFormat.ADD_CLOSING, LetterFormat.ADD_SIGNATURE);
        System.out.println("enum letter = \n" + formatLetterEnums("This is awesome!!", formats));

    }

    enum LetterFormat {
        ADD_SALUTATION_M,
        ADD_SALUTATION_F,
        ADD_CLOSING,
        ADD_SIGNATURE
    }
}
