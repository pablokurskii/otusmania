package main;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class Atm {
    private int balance;
    private final Map<Nominal, Integer> noteCellsBalance;
    private Banknotes banknotes;

    public Atm() {
        noteCellsBalance = new EnumMap<>(Nominal.class);
        for (Nominal nominal : Nominal.values()) {
            noteCellsBalance.put(nominal, 0);
        }
    }

    public Map<Nominal, Integer> getNoteCellsBalance() {
        return noteCellsBalance;
    }

    public void insertNotes(Banknotes insertedBanknotes) {
        System.out.println("Banknotes inserted.");
        //is this a right way of copying??
        banknotes = insertedBanknotes;
        noteCellsBalance.putAll(banknotes.getCash());
        System.out.println("Processing... Banknotes accepted.");
        setBalance();
        printBalance();
    }

    public int getBalance() {
        return balance;
    }

    public void printBalance() {
        System.out.printf(Locale.US, "Your balance is %,d\n", getBalance());
    }

    private void setBalance() {
        this.balance = banknotes.getTotalAmount();
    }

    private void reduceBalance(int amount){
        balance-=amount;
    }

    public int withdrawAll() {
        System.out.println("Processing withdraw...");
        int amountAvailable = getBalance();
        reduceBalance(amountAvailable);
        System.out.printf(Locale.US, "Amount of withdraw: %,d. Available amount: 0,00\n", amountAvailable);
        return amountAvailable;
    }

    public boolean withdrawAmount(Nominal nominal, int amount) {
        if (amount > balance) {
            System.out.println("Requested amount exceeded your balance.");
            return false;
        }

        if (nominal == Nominal.ONE_THOUSAND
                && noteCellsBalance.get(nominal) >= amount / 1000) {
            System.out.printf("Processing... Take %d banknotes with nominal %s\n", amount / 1000, nominal);
            reduceBalance(amount);
            return true;
        }

        if (nominal == Nominal.TWO_HUNDRED
                && noteCellsBalance.get(nominal) >= amount / 200) {
            System.out.printf("Processing... Take %d banknotes with nominal %s\n", amount / 200, nominal);
            reduceBalance(amount);
            return true;
        }

        if (nominal == Nominal.FIFTY
                && noteCellsBalance.get(nominal) >= amount / 50) {
            System.out.printf("Processing... Take %d banknotes with nominal %s\n", amount / 50, nominal);
            reduceBalance(amount);
            return true;
        }
        System.out.printf("Sorry. No banknotes available of %s nominal for requested amount.",nominal);
        return false;

    }
}
