package kz.alseco.processor.homework;

import kz.alseco.model.Message;
import kz.alseco.processor.Processor;

public class ThrowExceptionEveryEvenSecondTimeHelper implements Processor {

    private final TimeHelper timeHelper;

    public ThrowExceptionEveryEvenSecondTimeHelper(TimeHelper timeHelper) {
        this.timeHelper = timeHelper;
    }

    @Override
    public Message process(Message message) {
        int currentSecond = timeHelper.getCurrentSecond();
        if (currentSecond % 2 == 0) {
            throw new EvenSecondException(currentSecond);
        }
        return message.toBuilder().build();
    }
}
