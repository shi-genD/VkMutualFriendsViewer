package main.utils;

/**
 * Created by shi on 27.09.16.
 */
public class MyWrapper<T> {
    private T value;

    public MyWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
