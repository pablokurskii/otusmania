package ru.otus.listener;

import ru.otus.model.Message;

import java.util.ArrayDeque;
import java.util.Deque;

public class ListenerMemento implements Listener {

    private final Deque<Message> messageHistory;

    public ListenerMemento() {
        this.messageHistory = new ArrayDeque<>();
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        storeMessage(oldMsg);
    }

    private void storeMessage(Message msg){
        messageHistory.add(msg);
    }

    public Message getLastMessage(){
        return messageHistory.getLast();
    }
}
