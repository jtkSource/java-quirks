package com.jtk;

/**
 * Created by jubin on 5/7/2017.
 */

public class Entity {
    private boolean aBoolean;
    private char charKey = 'a';
    private long aLong;
    private float floatingValue;
    private double doubleValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (aBoolean != entity.aBoolean) return false;
        if (charKey != entity.charKey) return false;
        if (aLong != entity.aLong) return false;
        if (Float.compare(entity.floatingValue, floatingValue) != 0) return false;
        return Double.compare(entity.doubleValue, doubleValue) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (aBoolean ? 1 : 0);
        result = 31 * result + (int) charKey;
        result = 31 * result + (int) (aLong ^ (aLong >>> 32));
        result = 31 * result + (floatingValue != +0.0f ? Float.floatToIntBits(floatingValue) : 0);
        temp = Double.doubleToLongBits(doubleValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
