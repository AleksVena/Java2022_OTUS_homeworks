package kz.alseco.listener.homework;

import kz.alseco.model.Message;

import java.util.List;

public interface Storage<T> {

    void save(Message oldMessage, Message newMessage);

    List<T> getElements();

}
