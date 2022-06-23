package kz.alseco.listener.homework;

import kz.alseco.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
