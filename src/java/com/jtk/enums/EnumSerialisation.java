package com.jtk.enums;

import java.io.*;

/**
 * Created by jubin on 7/7/2017.
 */
public class EnumSerialisation {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        WEEKEND sunday = WEEKEND.SUNDAY;
        System.out.println(sunday.name());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(sunday);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(bytes);
        ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        WEEKEND object = (WEEKEND) objectInputStream.readObject();
        System.out.println("object.name() = " + object.name());
        System.out.println("(object==sunday) = " + (object==sunday));
    }

}
enum WEEKEND{
    SATURDAY,
    SUNDAY
}