package main.apivk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.util.EntityUtils;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Created by shi on 15.09.16.
 */
public class APIvk {


    public List<String> getFriends(String id) throws URISyntaxException, HttpException, IOException, JSONException {
        String URL = "https://api.vk.com/method/friends.get?user_id="+id;
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject json = new JSONObject(responseString);
        JSONArray jarr = json.getJSONArray("response");
        List<String> friendList = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++){
            friendList.add(jarr.get(i).toString());
            System.out.print(jarr.get(i).toString() + " ");
        }
        System.out.println();
        return friendList;
    }

    public Set<String> getMutualFriends(String id1, String id2) throws URISyntaxException, HttpException, IOException, JSONException{
        Set<String> mutualFriends = new HashSet<>();
        mutualFriends.addAll(getFriends(id1));
        mutualFriends.retainAll(getFriends(id2));
        System.out.println("Number: "+mutualFriends.size());
        System.out.println(mutualFriends.toString());
        return mutualFriends;
    }

    public VKUser getUser(String id) throws URISyntaxException, HttpException, IOException, JSONException {
        String URL = "https://api.vk.com/method/users.get?user_id="+id+"&fields=photo_100";
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        VKUser user = new VKUser();
        user.parseAndSetUser(responseString);
        return user;
    }

  /*  private String client_id = "5629635";
    private String scope = "friends";
    private String redirect_uri = "http://oauth.vk.com/blank.html";
    private String display = "page";
    private String response_type = "token";
    private String access_token;
    private String email = "************";//тут должен быть прописан email
    private String pass = "**********";//тут должен быть прописан пароль

    public void setConnection() throws IOException, URISyntaxException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        // Делаем первый запрос
        HttpPost post = new HttpPost("http://oauth.vk.com/authorize?" +
                "client_id=" + client_id +
                "&scope=" + scope +
                "&redirect_uri=" + redirect_uri +
                "&display=" + display +
                "&response_type=" + response_type);
        HttpResponse response;
        response = httpClient.execute(post);
        post.abort();
        //Получаем редирект
        String HeaderLocation = response.getFirstHeader("location").getValue();
        URI RedirectUri = new URI(HeaderLocation);
        //Для запроса авторизации необходимо два параметра полученных в первом запросе
//ip_h и to_h
        String ip_h = RedirectUri.getQuery().split("&")[2].split("=")[1];
        String to_h = RedirectUri.getQuery().split("&")[4].split("=")[1];

// Делаем запрос авторизации
        post = new HttpPost("https://login.vk.com/?act=login&soft=1" +
                "&q=1" +
                "&ip_h=" + ip_h +
                "&from_host=oauth.vk.com" +
                "&to=" + to_h +
                "&expire=0" +
                "&email=" + email +
                "&pass=" + pass);
        response = httpClient.execute(post);
        post.abort();
// Получили редирект на подтверждение требований приложения
        HeaderLocation = response.getFirstHeader("location").getValue();
        post = new HttpPost(HeaderLocation);
// Проходим по нему
        response = httpClient.execute(post);
        post.abort();
// Теперь последний редирект на получение токена
        HeaderLocation = response.getFirstHeader("location").getValue();
// Проходим по нему
        post = new HttpPost(HeaderLocation);
        response = httpClient.execute(post);
        post.abort();
// Теперь в след редиректе необходимый токен
        HeaderLocation = response.getFirstHeader("location").getValue();
// Просто спарсим его сплитами
        access_token = HeaderLocation.split("#")[1].split("&")[0].split("=")[1];

        System.out.println(access_token);
    }*/

}
