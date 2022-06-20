package kz.alseco.listener.homework;

import kz.alseco.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryStorage implements Storage<HistoryElement> {

    private final List<HistoryElement> elements = new ArrayList<>();

    @Override
    public void save(Message oldMessage, Message newMessage) {
        elements.add(HistoryElement.of(oldMessage, newMessage));
    }

    @Override
    public List<HistoryElement> getElements() {
        return Collections.unmodifiableList(elements);
    }
}
