package com.jtk;

import java.io.*;

/**
 * Created by jubin on 5/6/2017.
 */
public class Serialization101 {

    public String superText = "SUPER HELLO";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        InnerClassSerialization(byteArrayOutputStream, objectOutputStream);
        //serializeStaticInnerClass(objectOutputStream,byteArrayOutputStream);
        //outterSerializable(byteArrayOutputStream, objectOutputStream);
       // derivedClassSerialisation(byteArrayOutputStream, objectOutputStream);
    }

    // here the derived class variables are correctly saved where as the super class variables on deserilising will take
    //default constructor initialisation variables even if the derived class overrides it in its default constructor.
    // if super class implemented Serilisable then its properties would also be saved to the last state.
    private static void derivedClassSerialisation(ByteArrayOutputStream byteArrayOutputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        DerivedClassSerializable derivedClassSerializable = new DerivedClassSerializable("I'am base");
        objectOutputStream .writeObject(derivedClassSerializable);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        derivedClassSerializable = (DerivedClassSerializable) new ObjectInputStream(byteArrayInputStream).readObject();
        System.out.println(derivedClassSerializable.baseText); // I am base
        System.out.println(derivedClassSerializable.text); // I AM super
    }

    //works as the non-serilizable inner class instance is not instantiated
    private static void outterSerializable(ByteArrayOutputStream byteArrayOutputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        OuterSerializable outerSerializable = new OuterSerializable();
        objectOutputStream .writeObject(outerSerializable);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        outerSerializable = (OuterSerializable) new ObjectInputStream(byteArrayInputStream).readObject();
        System.out.println(outerSerializable.text);
    }

    //will throw NotSerializableException: com.jtk.Serialization101 as it has reference to the  outer class and  outter class si not serialisable
    private static void InnerClassSerialization(ByteArrayOutputStream byteArrayOutputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        Serialization101 serialization101 = new Serialization101();
        InnerClass innerClass = serialization101.new InnerClass();
        objectOutputStream.writeObject(innerClass);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        innerClass = (InnerClass) new ObjectInputStream(byteArrayInputStream).readObject();
        System.out.println(innerClass.innerText);
    }

    // will not throw NotSerializableException: com.jtk.Serialization101 as it doesn't have a reference to the outter class
    private static void serializeStaticInnerClass(ObjectOutputStream objectOutputStream, ByteArrayOutputStream byteArrayOutputStream) throws IOException, ClassNotFoundException {
        StaticSerializable someSerializable = new StaticSerializable();
        objectOutputStream.writeObject(someSerializable);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        someSerializable = (StaticSerializable) new ObjectInputStream(byteArrayInputStream).readObject();
        System.out.println(someSerializable.staticText);

    }

    public static class StaticSerializable implements Serializable {
        public String staticText = "Hello";
    }

    class InnerClass implements Serializable {
        public String innerText = "InnerClassText";
    }
}

class OuterSerializable implements  Serializable{

    public String text = "OutterSerilizable";


    public class InnerClass {

        public String text = "InnerSerializable";

    }
}

class SuperClassNonSerializable {
    protected String text;
    public SuperClassNonSerializable(){
        text="I AM super";
    }
    public SuperClassNonSerializable(String msg){
        this.text=msg;
    }
}
class DerivedClassSerializable extends SuperClassNonSerializable implements Serializable{
    public String baseText = " I am not Super";
    public DerivedClassSerializable(String msg){
        super(msg);
        this.baseText=msg;
        this.text="I am base constructor";
    }

}