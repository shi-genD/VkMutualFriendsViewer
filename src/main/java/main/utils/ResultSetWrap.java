package main.utils;

import main.apivk.VkUser;

import java.util.List;
import java.util.Set;

/**
 * Created by shi on 27.09.16.
 */
public class ResultSetWrap {
    private Set<VkUser> resultSet;
    private UserNotFoundException exception;
    private boolean isSuccess = false;


    public ResultSetWrap(Set<VkUser> resultSet) {
        this.resultSet = resultSet;
        this.isSuccess = true;
    }

    public ResultSetWrap(UserNotFoundException exception) {
        this.exception = exception;
    }

    public Set<VkUser> getResultSet() {
        return resultSet;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public UserNotFoundException getException() {
        return exception;
    }

}
