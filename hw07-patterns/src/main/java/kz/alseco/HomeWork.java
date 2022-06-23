package kz.alseco;

import kz.alseco.handler.ComplexProcessor;
import kz.alseco.listener.homework.HistoryListener;
import kz.alseco.model.Message;
import kz.alseco.model.ObjectForMessage;
import kz.alseco.processor.ProcessorChangeFields;
import kz.alseco.processor.ProcessorExceptionOnEvenSecond;

import java.time.LocalDateTime;
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
        var processors = List.of(new ProcessorChangeFields(),
                new ProcessorExceptionOnEvenSecond(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new HistoryListener();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field11("field1")
                .field12("field2")
                .field13(new ObjectForMessage())
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
