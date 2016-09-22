package main.utils;

/**
 * Created by shi on 21.09.16.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String str) {
        super(str);
    }
}
