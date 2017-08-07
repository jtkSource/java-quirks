package com.jtk.clone;

/**
 * Created by jubin on 7/8/2017.
 */
public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        DerivedClass derivedClass = new DerivedClass(new SomeClass("Hello"), 2, "Bye");
        derivedClass.setS2("Jubin");
        derivedClass.getS1().addToStringList("yo");
        DerivedClass clonedDerived = derivedClass.clone();
        System.out.println("derivedClass.toString() = " + derivedClass.toString());
        System.out.println("clonedDerived.toString() = " + clonedDerived.toString());

        System.out.println("(clonedDerived == derivedClass) = " + (clonedDerived == derivedClass));
        System.out.println("(clonedDerived.getS1() == derivedClass.getS1()) = " + (clonedDerived.getS1() == derivedClass.getS1()));
        System.out.println("(clonedDerived.getS1().getStringList()== derivedClass.getS1().getStringList()) = " + (clonedDerived.getS1().getStringList()== derivedClass.getS1().getStringList()));

    }
}

class DerivedClass extends BaseClass implements Cloneable {

    private final String s3;

    public DerivedClass(SomeClass s1, int index, String s3) {
        super(s1, index);
        this.s3 = s3;
    }

    @Override
    public SomeClass getS1() {
        return super.getS1();
    }

    @Override
    protected void setS2(String s2) {
        super.setS2(s2);
    }

    @Override
    protected DerivedClass clone() throws CloneNotSupportedException {
        DerivedClass derivedClass = (DerivedClass) super.clone();
        derivedClass.setS1(this.getS1().clone());
        return derivedClass;
    }

    @Override
    public String toString() {
        return super.toString() + " -> DerivedClass{" +
                "s3='" + s3 + '\'' +
                '}';
    }
}