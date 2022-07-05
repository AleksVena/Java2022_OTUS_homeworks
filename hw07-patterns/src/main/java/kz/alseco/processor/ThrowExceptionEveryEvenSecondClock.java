package kz.alseco.processor;

public class ThrowExceptionEveryEvenSecondClock extends RuntimeException {

    public ThrowExceptionEveryEvenSecondClock(int currentSecond) {
        super("Current second is: " + currentSecond);
    }

}