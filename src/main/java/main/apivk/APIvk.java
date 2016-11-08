package main.apivk;

import main.utils.MutualFriendsEvaluationException;
import main.utils.ResultSetWrap;
import main.utils.StringParser;
import main.utils.UserNotFoundException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created by shi on 21.09.16.
 */
public class APIvk {

    public static VkUser parseInputId(String str, int n) throws UserNotFoundException{
        if (str == null || str.length() == 0)
            throw new UserNotFoundException("Invalid user id", n);
        StringParser sp = new StringParser(str);
        if (sp.checkUrl())
            if (sp.checkString())
                return getId(str, n);
            else
                return getId(sp.getString(), n);
        else
            throw new UserNotFoundException("Invalid user id", n);
    }

    private static VkUser getId(String shortLink, int n) throws UserNotFoundException{
        try {
            String URL = "https://api.vk.com/method/users.get?user_ids=" + shortLink + "&fields=photo_100";
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(URL));
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject json = new JSONObject(responseString);
            JSONArray jarr = json.getJSONArray("response");
            JSONObject jo = new JSONObject(jarr.get(0).toString());
            if (jo.has("deactivated")) {
                throw new UserNotFoundException("User " + jo.getString("deactivated"), n);
            }
            return VkUser.parse(jo.toString());
        }  catch (JSONException e) {
            throw new UserNotFoundException("Invalid user id", n);
        } catch (IOException e) {
            throw new MutualFriendsEvaluationException(e);
        }

    }

    private static List<VkUser> getFriends(VkUser user) throws IOException, JSONException {
        String URL = "https://api.vk.com/method/friends.get?user_id="+user.getUserId()+"&fields=photo_100";
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject json = new JSONObject(responseString);
        JSONArray jarr = json.getJSONArray("response");
        List<VkUser> friendList = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++){
            friendList.add(VkUser.parse(jarr.get(i).toString()));
        }
        return friendList;
    }

    public static ResultSetWrap getMutualFriends(VkUser user1, VkUser user2){
        Set<VkUser> mutualFriends = new HashSet<>();
        try {
            mutualFriends.addAll(getFriends(user1));
            mutualFriends.retainAll(getFriends(user2));
            return new ResultSetWrap(mutualFriends);
        } catch (IOException | JSONException e) {
            throw new MutualFriendsEvaluationException(e);
        }
    }
}
