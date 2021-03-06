package main;



public enum Nominal {
    FIFTY(50), TWO_HUNDRED(200), FIVE_HUNDRED(500), ONE_THOUSAND(1000);

    private final int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
