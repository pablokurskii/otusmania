package main;

public interface AtmInterface {
    void insertNotes(Banknotes insertedBanknotes);

    int getBalance();

    void printBalance();

    int withdrawAll();

    boolean withdrawAmount(Nominal nominal, int amount);
}
