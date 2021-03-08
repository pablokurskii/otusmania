package ru.otus.processor;

import ru.otus.model.Message;

import java.util.Calendar;

public class ProcessorThrowExceptionEvenSecond implements Processor {

    @Override
    public Message process(Message message) {
        Calendar rightNow = Calendar.getInstance();

        int secondOfTime = rightNow.get(Calendar.SECOND);
        if (secondOfTime % 2 == 0) throw new RuntimeException("ProcessorThrowExceptionEvenSecond");
        return message;
    }
}
