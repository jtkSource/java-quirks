package com.jtk.autoclose;

/**
 * Created by jubin on 7/8/2017.
 */
public class CustomAutoCloseable implements AutoCloseable {

    public void beforeCloseWithException() throws Exception {
        System.out.println("beforeClose throws exception");
        throw new Exception("BeforeClose Exception");
    }

    public void beforeClose() {
        System.out.println("beforeClose doesnt throw exception");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closed with exception");
        throw new Exception("CloseException from close()");
    }
    public static void main(String[] args) {
        try(CustomAutoCloseable customAutoCloseable = new CustomAutoCloseable()){
            customAutoCloseable.beforeClose();
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        System.out.println("----------------------------------------------------");
        try(CustomAutoCloseable customAutoCloseable = new CustomAutoCloseable()){
            customAutoCloseable.beforeCloseWithException();
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            int suppressedCount = e.getSuppressed().length;
            for (int i=0; i<suppressedCount; i++){
                System.out.println("Suppressed: " + e.getSuppressed()[i]);
            }
        }
    }
}
