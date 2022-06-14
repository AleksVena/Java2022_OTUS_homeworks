package kz.alseco;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException() {
        super("Недостаточно денег");
    }
}
