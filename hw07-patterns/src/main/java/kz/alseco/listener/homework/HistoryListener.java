package kz.alseco.listener.homework;

import kz.alseco.listener.Listener;
import kz.alseco.model.Message;

public class HistoryListener implements Listener {

    private final Storage<?> storage;

    public HistoryListener(Storage<?> storage) {
        this.storage = storage;
    }

    @Override
    public void onUpdated(Message oldMess, Message newMess) {
        storage.save(oldMess.deepCopy(), newMess.deepCopy());
    }
}
