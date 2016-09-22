package main.utils;

/**
 * Created by shi on 21.09.16.
 */
public class UserNotFoundException extends Exception {

    private int number;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String str, int n) {
        super(str);
        this.number = n;
    }

    public int getNumber() { return number; }
}
