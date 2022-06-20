package kz.alseco.listener;

import kz.alseco.model.Message;

public class ListenerPrinter implements Listener {

    @Override
    public void onUpdated(Message oldMess, Message newMess) {
        var logString = String.format("oldMess:%s, newMess:%s", oldMess, newMess);
        System.out.println(logString);
    }
}
