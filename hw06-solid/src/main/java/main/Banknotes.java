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

        cash.forEach((nominal, quantity) -> {
            if (nominal.equals(Nominal.FIFTY)) {
                totalAmount += 50 * quantity;
            } else if (nominal.equals(Nominal.TWO_HUNDRED)) {
                totalAmount += 200 * quantity;
            } else if (nominal.equals(Nominal.ONE_THOUSAND)) {
                totalAmount += 1000 * quantity;
            } else throw new RuntimeException("Wrong nominal");
        });
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
