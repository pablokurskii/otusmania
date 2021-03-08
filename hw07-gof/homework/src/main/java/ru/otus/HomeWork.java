package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinter;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.*;

import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */

        var processorsHW = List.of(new ProcessorChangePlacesFor11And12Fields(),
                new ProcessorThrowExceptionEvenSecond());

        var complexProcessor = new ComplexProcessor(processorsHW, ex -> System.out.println(ex.getMessage()));
        var listenerPrinter = new ListenerPrinter();
        complexProcessor.addListener(listenerPrinter);

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("Hello","mars"));

        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);

    }
}
