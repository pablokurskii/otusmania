package main;

import java.util.EnumMap;
import java.util.Map;

public class Banknotes {
    private final Map<Nominal, Integer> cash;
    private int totalAmount;

    public Banknotes() {
        totalAmount = 0;
        cash = new EnumMap<>(Nominal.class);
    }

    public void setCash(Nominal nominal, Integer quantity) {
        cash.put(nominal, quantity);
    }

    public Map<Nominal, Integer> getCash(){
        Map<Nominal, Integer> shallowCash = new EnumMap<>(Nominal.class);
        shallowCash.putAll(cash);
        return shallowCash;
    }

    public void countCash() {
        totalAmount = cash.entrySet().stream().mapToInt(entry -> entry.getKey().getValue() * entry.getValue()).sum();
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
