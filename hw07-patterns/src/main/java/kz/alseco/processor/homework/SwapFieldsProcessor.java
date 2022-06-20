package kz.alseco.processor.homework;

import kz.alseco.model.Message;
import kz.alseco.processor.Processor;

public class SwapFieldsProcessor implements Processor {

    @Override
    public Message process(Message message) {
        var currentField11 = message.getField11();
        var currentField12 = message.getField12();

        return message.toBuilder()
                .field11(currentField12)
                .field12(currentField11)
                .build();
    }
}
