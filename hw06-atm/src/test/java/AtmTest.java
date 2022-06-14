import kz.alseco.Atm;
import kz.alseco.Banknote;
import kz.alseco.Nominal;
import kz.alseco.NotEnoughMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AtmTest {
    private Atm atm;

    @BeforeEach
    void setUp() {
        atm = new Atm(
                List.of(
                        new Banknote(20000),
                        new Banknote(10000),
                        new Banknote(5000),
                        new Banknote(2000)
                )
        );
    }

    @Test
    @DisplayName("Проверим сумму и наличие нужных банкнот")
    void initial() {
        assertThat(atm.getBalance()).isEqualTo(37000);

        assertThat(atm.getBalanceCell())
                .containsOnly(
                        entry(Nominal.FIVE_THOUSAND, 5000),
                        entry(Nominal.TWENTY_THOUSAND, 20000),
                        entry(Nominal.TEN_THOUSAND, 10000),
                        entry(Nominal.TWO_THOUSAND, 2000)
                );
    }

    @Test
    @DisplayName("Проверим пополнение")
    void cashIn() {
        atm.cashIn(
                List.of(
                        new Banknote(2000),
                        new Banknote(2000),
                        new Banknote(2000),
                        new Banknote(5000)
                )
        );

        assertThat(atm.getBalance()).isEqualTo(48000);

        assertThat(atm.getBalanceCell())
                .containsOnly(
                        entry(Nominal.FIVE_THOUSAND, 10_000),
                        entry(Nominal.TWO_THOUSAND, 8_000),
                        entry(Nominal.TWENTY_THOUSAND, 20_000),
                        entry(Nominal.TEN_THOUSAND, 10_000)
                );
    }

    @Test
    @DisplayName("Проверим выдачу")
    void cashOut() {
        List<Banknote> banknotes = atm.cashOut(17000);
        assertThat(banknotes).hasSize(3);

        assertThat(banknotes)
                .extracting("nominal", "value")
                .contains(
                        tuple(Nominal.TWO_THOUSAND, 2_000),
                        tuple(Nominal.TEN_THOUSAND, 10_000),
                        tuple(Nominal.FIVE_THOUSAND, 5_000)
                );

        assertThat(atm.getBalance()).isEqualTo(20_000);

        assertThat(atm.getBalanceCell())
                .containsOnly(
                        entry(Nominal.TWO_THOUSAND, 0),
                        entry(Nominal.FIVE_THOUSAND, 0),
                        entry(Nominal.TEN_THOUSAND, 0),
                        entry(Nominal.TWENTY_THOUSAND, 20_000)
                );
    }

    @Test
    @DisplayName("Проверим недостаточность средств")
    void notEnoughMoney() {
        assertThatThrownBy( () -> atm.cashOut(100_500))
                .isInstanceOf(NotEnoughMoneyException.class)
                .hasMessage("Недостаточно денег");
    }
}
