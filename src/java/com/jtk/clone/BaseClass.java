package com.jtk.clone;

public class BaseClass {
    private SomeClass s1;
    private final int index;
    private String s2;

    public BaseClass(SomeClass s1, int index) {
        this.s1 = s1;
        this.index = index;
    }

    public String getS2() {
        return s2;
    }

    void setS2(String s2) {
        this.s2 = s2;
    }

    protected void setS1(SomeClass s1) {
        this.s1 = s1;
    }

    protected SomeClass getS1() {
        return s1;
    }

    @Override
    public String toString() {
        return "BaseClass{" +
                "s1=" + s1 +
                ", index=" + index +
                ", s2='" + s2 + '\'' +
                '}';
    }
}
