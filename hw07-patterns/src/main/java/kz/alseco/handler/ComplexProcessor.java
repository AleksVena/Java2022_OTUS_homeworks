package kz.alseco.handler;

import kz.alseco.model.Message;
import kz.alseco.listener.Listener;
import kz.alseco.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComplexProcessor implements Handler {

    private final List<Listener> listeners = new ArrayList<>();
    private final List<Processor> processors;
    private final Consumer<Exception> errorHandler;

    public ComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }

    @Override
    public Message handle(Message msg) {
        Message newMess = msg;
        for (Processor pros : processors) {
            try {
                newMess = pros.process(newMess);
            } catch (Exception ex) {
                errorHandler.accept(ex);
            }
        }
        notify(msg, newMess);
        return newMess;
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notify(Message oldMess, Message newMess) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdated(oldMess, newMess);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
