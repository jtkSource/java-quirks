package com.jtk.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * Created by jubin on 7/8/2017.
 */
public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(7);
        buffer.put("Jubin".getBytes());
        System.out.println("buffer.limit() = " + buffer.limit(5));
        System.out.println("buffer.position() = " + buffer.position());
        System.out.println("buffer.capacity() = " + buffer.capacity());
        System.out.println("buffer.remaining() = " + buffer.remaining());


        byte[] byteBuffer = new byte[200];
        ByteBuffer buffer1 = ByteBuffer.wrap(byteBuffer,10,50);
        buffer1.limit(20);
        bufferStats(buffer1);

        ByteBuffer duplicateBuffer = buffer1.duplicate();
        bufferStats(duplicateBuffer);

        CharBuffer charBuffer = buffer1.asCharBuffer();
        bufferStats(charBuffer);

        charBuffer.put('G').put('H');
        bufferStats(charBuffer);

        charBuffer.flip();// moves the position to 0 and limit to the last position - 2
        bufferStats(charBuffer);

        charBuffer.position(2);
        bufferStats(charBuffer);
        charBuffer.compact();
        bufferStats(charBuffer);

        System.out.println("ByteOrder.nativeOrder() = " + ByteOrder.nativeOrder());


    }

    private static void bufferStats(Buffer buffer) {
        System.out.println("---------------------------------------------");
        System.out.println("charBuffer.capacity() = " + buffer.capacity());
        System.out.println("charBuffer.limit() = " + buffer.limit());
        System.out.println("charBuffer.position() = " + buffer.position());
        System.out.println("charBuffer.remaining() = " + buffer.remaining());
    }
}
