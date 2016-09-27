package main.net;

import main.apivk.APIvk;
import main.apivk.VkUser;
import main.utils.MyWrapper;
import main.utils.UserNotFoundException;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by shi on 27.09.16.
 */
public class MutualFriendsCallable implements Callable<MyWrapper> {
    private String firstId;
    private String secondId;

    public MutualFriendsCallable(String id1, String id2){
        firstId = id1;
        secondId = id2;
    }

    @Override
    public MyWrapper call() throws Exception {
        APIvk apiVk = new APIvk();
        try {
            String id1 = apiVk.parseInputId(firstId, 0);
            String id2 = apiVk.parseInputId(secondId, 1);
            MyWrapper<Set<VkUser>> result = new MyWrapper<>(apiVk.getMutualFriends(id1, id2));
            return result;
        } catch (UserNotFoundException e) {
            MyWrapper<UserNotFoundException> result = new MyWrapper<>(e);
            return result;
        }
    }
}
