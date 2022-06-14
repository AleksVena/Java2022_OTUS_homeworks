package kz.alseco;

import java.util.Arrays;

public enum Nominal {

    FIVE_HUNDRED(500),

    THOUSAND(1000),

    TWO_THOUSAND(2000),

    FIVE_THOUSAND(5000),

    TEN_THOUSAND(10000),

    TWENTY_THOUSAND(20000);

    private final int value;

    Nominal(int value) {
        this.value = value;
    }

    public static Nominal of(int value) {
        return Arrays.stream(Nominal.values())
                .filter(it -> it.value == value)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Нет подходящего номинала: " + value));
    }

    public int getValue() {
        return value;
    }
}
