package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmTest {

    private Atm atm;
    private Banknotes banknotes;

    @BeforeEach
    void setUp() {
        banknotes = new Banknotes();

        banknotes.setCash(Nominal.FIFTY, 2);
        banknotes.setCash(Nominal.ONE_THOUSAND, 5);
        banknotes.setCash(Nominal.TWO_HUNDRED, 8);
        banknotes.setCash(Nominal.FIVE_HUNDRED, 3);
        banknotes.countCash();

        atm = new Atm(banknotes);
    }

    @Test
    @DisplayName("Top up account")
    void topUpAccount(TestInfo testInfo) {
        System.out.println("Starting test: " + testInfo.getDisplayName());
        assertThat(atm.getBalance()).isEqualTo(banknotes.getTotalAmount());
    }

    @Test
    @DisplayName("Withdraw amount by nominal from account")
    void withdrawAmountByNominalFromAccount(TestInfo testInfo) {
        System.out.println("Starting test: " + testInfo.getDisplayName());

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

        //when
        withdrawAmount = 1000;
        withdrawResult = atm.withdrawAmount(Nominal.FIVE_HUNDRED, withdrawAmount);
        //then
        assertThat(withdrawResult).isTrue();
    }


    @Test
    @DisplayName("Withdraw from account")
    void withdrawFromAccount(TestInfo testInfo) {
        System.out.println("Starting test: " + testInfo.getDisplayName());

        //given
        int topUpAmount = banknotes.getTotalAmount();
        //then
        assertThat(topUpAmount).isEqualTo(atm.withdrawAll());
    }


}
