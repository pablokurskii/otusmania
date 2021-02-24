package main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmTest {
    @Test
    @DisplayName("Top up account")
    void topUpAccount(){
        //given
        Atm atm = new Atm();
        Banknotes banknotes = new Banknotes();

        banknotes.setCash(Nominal.FIFTY, 2);
        banknotes.setCash(Nominal.ONE_THOUSAND, 5);
        banknotes.setCash(Nominal.TWO_HUNDRED, 8);

        //when
        atm.insertNotes(banknotes);

        //then
        assertThat(atm.getNoteCellsBalance()).isEqualTo(banknotes.getCash());
    }

    @Test
    @DisplayName("Withdraw amount by nominal from account")
    void withdrawAmountFromAccount(){
        //given
        Atm atm = new Atm();
        Banknotes banknotes = new Banknotes();

        banknotes.setCash(Nominal.ONE_THOUSAND, 5);
        banknotes.setCash(Nominal.TWO_HUNDRED, 8);
        banknotes.setCash(Nominal.FIFTY, 2);
        banknotes.countCash();

        atm.insertNotes(banknotes);
        //when
        int withdrawAmount = 5000;
        boolean withdrawResult = atm.withdrawAmount(Nominal.ONE_THOUSAND, withdrawAmount);

        //then
        assertThat(withdrawResult).isTrue();

        //when
        withdrawAmount = 1600;
        withdrawResult = atm.withdrawAmount(Nominal.TWO_HUNDRED, withdrawAmount);

        //then
        assertThat(withdrawResult).isTrue();

        //when
        withdrawAmount = 50;
        withdrawResult = atm.withdrawAmount(Nominal.FIFTY, withdrawAmount);

        //then
        assertThat(withdrawResult).isTrue();
    }


    @Test
    @DisplayName("Withdraw from account")
    void withdrawFromAccount(){
        //given
        Atm atm = new Atm();
        Banknotes banknotes = new Banknotes();

        banknotes.setCash(Nominal.FIFTY, 2);
        banknotes.setCash(Nominal.ONE_THOUSAND, 5);
        banknotes.setCash(Nominal.TWO_HUNDRED, 8);
        banknotes.countCash();
        int topUpAmount = banknotes.getTotalAmount();
        //when
        atm.insertNotes(banknotes);

        //then
        assertThat(topUpAmount).isEqualTo(atm.withdrawAll());
    }


}
