package main;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class Atm implements AtmInterface {
    private final Map<Nominal, Integer> noteCellsBalance;

    public Atm(Banknotes insertedBanknotes) {
        this.noteCellsBalance = new EnumMap<>(Nominal.class);
        insertNotes(insertedBanknotes);
    }

    private void empty(){
        noteCellsBalance.clear();
    }

    private void reduceBanknotes(Nominal nominal, int quantity){
        noteCellsBalance.computeIfPresent(nominal, (k, v) -> v - quantity);
    }

    @Override
    public void insertNotes(Banknotes insertedBanknotes) {
        System.out.println("Banknotes inserted.");
        noteCellsBalance.putAll(insertedBanknotes.getCash());
        System.out.println("Processing... Banknotes accepted.");
        printBalance();
    }

    @Override
    public int getBalance() {
        return noteCellsBalance.entrySet().stream().mapToInt(entry -> entry.getKey().getValue() * entry.getValue()).sum();
    }

    @Override
    public void printBalance() {
        System.out.printf(Locale.US, "Your balance is %,d\n", getBalance());
    }

    @Override
    public int withdrawAll() {
        System.out.println("Processing withdraw...");
        int amountAvailable = getBalance();
        empty();
        System.out.printf(Locale.US, "Amount of withdraw: %,d. Available amount: 0,00\n", amountAvailable);
        return amountAvailable;
    }

    @Override
    public boolean withdrawAmount(Nominal nominal, int amount) {
        if (amount > getBalance()) {
            System.out.println("Requested amount exceeded your balance.");
            return false;
        }

        if (noteCellsBalance.get(nominal) >= amount / nominal.getValue()) {
            System.out.printf("Processing... Take %d banknotes with nominal %s\n", amount / nominal.getValue(), nominal);
            reduceBanknotes(nominal, amount / nominal.getValue());
            return true;
        }

        System.out.printf("Sorry. No banknotes available of %s nominal for requested amount.",nominal);
        return false;

    }
}
