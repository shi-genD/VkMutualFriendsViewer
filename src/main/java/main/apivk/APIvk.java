package main.apivk;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shi on 21.09.16.
 */
public class APIvk {

    public List<VkUser> getFriends(String id) throws URISyntaxException, HttpException, IOException, JSONException {
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
            System.out.print(jarr.get(i).toString() + " ");
        }
        System.out.println();
        return friendList;
    }

    public Set<VkUser> getMutualFriends(String id1, String id2) throws URISyntaxException, HttpException, IOException, JSONException{
        Set<VkUser> mutualFriends = new HashSet<>();
        mutualFriends.addAll(getFriends(id1));
        mutualFriends.retainAll(getFriends(id2));
        System.out.println("Number: "+mutualFriends.size());
        System.out.println(mutualFriends.toString());
        return mutualFriends;
    }

    /*public VkUser getUser(String id) throws URISyntaxException, HttpException, IOException, JSONException {
        String URL = "https://api.vk.com/method/users.get?user_id="+id+"&fields=photo_100";
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        VkUser user = new VkUser();
        user.parseAndSetUser(responseString);
        return user;
    }*/
}
