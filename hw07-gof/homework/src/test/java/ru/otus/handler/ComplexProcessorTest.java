package ru.otus.handler;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.listener.Listener;
import ru.otus.processor.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(message, message);
    }

    @Test
    @DisplayName("2. Сделать процессор, который поменяет местами значения field11 и field12")
    void changeFieldsTest() {

        List<Processor> processors = List.of(new ProcessorChangePlacesFor11And12Fields());

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        assertThat(result.getField11()).isEqualTo(message.getField12());
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }

    @Test
    @DisplayName("3. выбрасывать исключение в четную секунду")
    void evenSecondExceptionText() {
        //given
        var evenSecond = (new Random().nextInt(60) * 2) % 60;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, evenSecond);

        var processor = new ProcessorThrowExceptionEvenSecond(calendar);
        var message = new Message.Builder(1L).build();

        //then
        assertThatThrownBy(() -> processor.process(message))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ProcessorThrowExceptionEvenSecond");
    }
}