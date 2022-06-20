package kz.alseco.processor.homework;

public class EvenSecondException extends RuntimeException {

    public EvenSecondException(int currentSecond) {
        super("Current second is: " + currentSecond);
    }

}
