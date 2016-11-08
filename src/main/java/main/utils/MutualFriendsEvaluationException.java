package main.utils;

/**
 * Created by shi on 29.09.16.
 */
public class MutualFriendsEvaluationException extends RuntimeException {

    private Throwable reason;

    public MutualFriendsEvaluationException(Throwable reason) {
        this.reason = reason;
    }

    public Throwable getReason() {
        return reason;
    }
}
