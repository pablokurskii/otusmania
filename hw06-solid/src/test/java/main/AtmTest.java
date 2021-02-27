package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmTest {

    private Atm atm;
    private Banknotes banknotes;

    @BeforeEach
    void setUp(){
        atm = new Atm();
        banknotes = new Banknotes();

        banknotes.setCash(Nominal.FIFTY, 2);
        banknotes.setCash(Nominal.ONE_THOUSAND, 5);
        banknotes.setCash(Nominal.TWO_HUNDRED, 8);
        banknotes.countCash();
    }

    @Test
    @DisplayName("Top up account")
    void topUpAccount(){
        //when
        atm.insertNotes(banknotes);

        //then
        assertThat(atm.getNoteCellsBalance()).isEqualTo(banknotes.getCash());
    }

    @Test
    @DisplayName("Withdraw amount by nominal from account")
    void withdrawAmountByNominalFromAccount(){
        //given
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
        int topUpAmount = banknotes.getTotalAmount();
        //when
        atm.insertNotes(banknotes);

        //then
        assertThat(topUpAmount).isEqualTo(atm.withdrawAll());
    }


}
