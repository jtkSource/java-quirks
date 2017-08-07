package com.jtk.enums;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by jubin on 6/17/2017.
 */
class ClassInitialisation {
    static private int i = 10;

    static {
        System.out.println("first static block");
    }

    {
        System.out.println("first instance block");
    }

    {
        System.out.println("second instance block");
    }

    public ClassInitialisation() {
        System.out.println("constructor " + i);
    }
}

public class Enum101 {


    public static void main(String[] args) {
        System.out.println("Constants.e = " + Constants.e);
        System.out.printf("Race.getTotalAverageHeight() = %f%n", Race.getTotalAverageHeight());
        System.out.println("new ClassInitialisation() = " + new ClassInitialisation());
        System.out.println("Race.getRaceFromRegion(\"AFRICA\") = " + Race.getRaceFromRegion("AFRICA"));

        Arrays.stream(War.values()).forEach(war -> {
            System.out.println(war.name()+ " war" + " war.apply() = " + war.apply());
        });
    }



    enum Race {

        ASIAN("ASIA", 5.7),
        EUROPEAN("EUROPE", 6.2),
        AFRICAN("AFRICA", 6),
        POLYNESIAN("PACIFIC", 5.4);
        private static HashMap<String, Race> raceHashMap = new HashMap<>();

        static {
            Arrays.stream(Race.values()).forEach(race -> {
                raceHashMap.put(race.region, race);
            });
        }

        private final String region;
        private final double averageHeight;

        Race(String region, double averageHeight) {

            System.out.println("intializing race region = " + region);
            this.region = region;
            this.averageHeight = averageHeight;
        }

        public static Race getRaceFromRegion(String region) {
            return raceHashMap.get(region);
        }

        public static double getTotalAverageHeight() {
            return Arrays.stream(Race.values())
                    .map(race -> race.averageHeight)
                    .reduce((aDouble, aDouble2) -> aDouble + aDouble2).get()
                    / Race.values().length;
        }

        public double getAverageHeight() {
            return averageHeight;
        }


    }
}


enum War{
    COLD(WarStrategy.STRATEGY1_2),
    WW1(WarStrategy.STRATEGY1),
    WW2(WarStrategy.STRATEGY2),
    MARSATTACK(WarStrategy.UNKNOWN);
    private final WarStrategy strategy;

    War(WarStrategy strategy1) {
        this.strategy=strategy1;
    }

    public String apply(){
        return strategy.warStrategy();
    }
}

enum WarStrategy {
    STRATEGY1 {
        @Override
        public String warStrategy() {
            return "Implement Strategy 1";
        }
    },
    STRATEGY1_2 {
        @Override
        String warStrategy() {
            return STRATEGY1.warStrategy() + " " + STRATEGY2.warStrategy();
        }
    },
    STRATEGY2 {
        @Override
        String warStrategy() {
            return "Implement Strategy 2";
        }
    },
    UNKNOWN{
        @Override
        String warStrategy() {
            return "GIVE UP";
        }
    };

    abstract String warStrategy();
}