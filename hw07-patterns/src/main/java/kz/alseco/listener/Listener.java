package kz.alseco.listener;

import kz.alseco.model.Message;

public interface Listener {

    void onUpdated(Message msg);

    //Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
    //Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
    //Для него уже есть тест, убедитесь, что тест проходит
}
