package com.jtk.enums;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by jubin on 6/18/2017.
 */
public class EnumMap1 {
    enum KEYS{
        PRIMARY_KEY,
        FOREIGN_KEY,
        INDEXED_KEY,
        CLUSTER_KEYS
    }
    public static void main(String[] args) {
        EnumMap<KEYS,List<String>> keysListEnumMap = new EnumMap<>(KEYS.class);
        keysListEnumMap.put(KEYS.PRIMARY_KEY, Arrays.asList("Jubin","a","b","C"));
        System.out.println("keysListEnumMap = " + keysListEnumMap);

    }
}
