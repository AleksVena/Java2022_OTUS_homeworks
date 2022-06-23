package kz.alseco.processor;

import kz.alseco.handler.ComplexProcessor;
import kz.alseco.model.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TestProcessorExceptionOnEvenSecond {

    @Test
    @DisplayName("Выбрасывает исключение, если секунда на момент запуска, четная (через Clock)")
    void shouldThrowExceptionIfCurrentSecondIsEvenClock() {
        var message = new Message.Builder(1L).build();

        var oddClock = Clock.fixed(
                Instant.parse("2022-06-23T12:00:03.000Z"),
                ZoneId.systemDefault()
        );
        var complexProcessorOdd = new ComplexProcessor(List.of(new ProcessorExceptionOnEvenSecond(oddClock)), (ex) -> {
            throw new TestProcessorExceptionOnEvenSecond.TestException(ex.getMessage());
        });

        assertThatCode(() -> complexProcessorOdd.handle(message)).doesNotThrowAnyException();

        var evenClock = Clock.fixed(
                Instant.parse("2022-06-23T12:00:02.000Z"),
                ZoneId.systemDefault()
        );
        var complexProcessorEven = new ComplexProcessor(List.of(new ProcessorExceptionOnEvenSecond(evenClock)), (ex) -> {
            throw new TestProcessorExceptionOnEvenSecond.TestException(ex.getMessage());
        });

        assertThatThrownBy(() -> complexProcessorEven.handle(message)).hasMessage("Current second is: 2");
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}
