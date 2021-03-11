package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinter;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.*;

import java.util.Calendar;
import java.util.List;

public class HomeWork {

    public static void main(String[] args) {
        var processorsHW = List.of(new ProcessorChangePlacesFor11And12Fields(),
                new ProcessorThrowExceptionEvenSecond(Calendar.getInstance()));

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
