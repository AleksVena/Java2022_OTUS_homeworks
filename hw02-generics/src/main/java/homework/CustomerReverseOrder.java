package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {
    private final Deque<Customer> store = new ArrayDeque<>();

    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        store.add(customer);
    }

    public Customer take() {
        return store.pollLast();
    }
}
