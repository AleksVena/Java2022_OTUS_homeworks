package kz.alseco.listener.homework;

import kz.alseco.listener.Listener;
import kz.alseco.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> messageMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg)
    {
        messageMap.put(msg.getId(), Message.copyMessage(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id)
    {
        return Optional.ofNullable(messageMap.get(id));
    }
}
