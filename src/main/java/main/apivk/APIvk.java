package main.apivk;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shi on 21.09.16.
 */
public class APIvk {

    public String parseInputId(String str, int n) throws UserNotFoundException, IOException{
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

    private String getId(String shortLink, int n) throws IOException, UserNotFoundException{
        try {
            String URL = "https://api.vk.com/method/users.get?user_ids=" + shortLink;
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
            return jo.getString("uid");
        }  catch (JSONException e) {
            throw new UserNotFoundException("Invalid user id", n);
        }

    }

    private List<VkUser> getFriends(String id) throws IOException, JSONException {
        String URL = "https://api.vk.com/method/friends.get?user_id="+id+"&fields=photo_100";
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject json = new JSONObject(responseString);
        JSONArray jarr = json.getJSONArray("response");
        List<VkUser> friendList = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++){
            friendList.add(new VkUser(jarr.get(i).toString()));
        }
        return friendList;
    }

    synchronized public Set<VkUser> getMutualFriends(String id1, String id2) throws IOException, JSONException{
        Set<VkUser> mutualFriends = new HashSet<>();
        mutualFriends.addAll(getFriends(id1));
        mutualFriends.retainAll(getFriends(id2));
        return mutualFriends;
    }

}
