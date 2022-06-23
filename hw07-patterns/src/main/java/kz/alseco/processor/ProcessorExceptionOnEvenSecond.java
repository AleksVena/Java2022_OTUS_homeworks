package kz.alseco.processor;

import kz.alseco.model.Message;

public class ProcessorExceptionOnEvenSecond implements Processor{
    private DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message)
    {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0)
            throw new RuntimeException("Even second exception");
        return message;
    }
}
