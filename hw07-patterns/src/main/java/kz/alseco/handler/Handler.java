package kz.alseco.handler;

import kz.alseco.model.Message;
import kz.alseco.listener.Listener;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
