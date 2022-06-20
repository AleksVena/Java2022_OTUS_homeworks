package kz.alseco;

import kz.alseco.handler.ComplexProcessor;
import kz.alseco.listener.homework.HistoryListener;
import kz.alseco.listener.homework.MemoryStorage;
import kz.alseco.model.Message;
import kz.alseco.model.ObjectForMessage;
import kz.alseco.processor.homework.SwapFieldsProcessor;
import kz.alseco.processor.homework.ThrowExceptionEveryEvenSecondClock;

import java.util.List;

public class HomeWork {

      /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        var processors = List.of(new ThrowExceptionEveryEvenSecondClock(),
                new SwapFieldsProcessor());

        var complexProcessor = new ComplexProcessor(processors, Throwable::printStackTrace);
        var storage = new MemoryStorage();
        var historyListener = new HistoryListener(storage);
        complexProcessor.addListener(historyListener);

        ObjectForMessage field13 = new ObjectForMessage();
        field13.setData(List.of("1", "2", "3"));

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var result = complexProcessor.handle(message);

        field13.setData(List.of("1", "2", "3", "4"));

        System.out.println("result:" + result);
        System.out.println("History: " + storage.getElements());

        complexProcessor.removeListener(historyListener);
    }
}
