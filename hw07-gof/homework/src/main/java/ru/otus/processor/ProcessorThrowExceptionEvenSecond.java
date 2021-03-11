package ru.otus.processor;

import ru.otus.model.Message;

import java.util.Calendar;

public class ProcessorThrowExceptionEvenSecond implements Processor {

    private final Calendar rightNow;

    public ProcessorThrowExceptionEvenSecond(Calendar rightNow) {
        this.rightNow = rightNow;
    }

    @Override
    public Message process(Message message) {

        int secondOfTime = rightNow.get(Calendar.SECOND);
        if (secondOfTime % 2 == 0) throw new RuntimeException("ProcessorThrowExceptionEvenSecond");
        return message;
    }
}
