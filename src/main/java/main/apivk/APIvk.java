package main.apivk;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by shi on 15.09.16.
 */
public class APIvk {

    private String client_id = "5629635";
    private String scope = "friends";
    private String redirect_uri = "http://oauth.vk.com/blank.html";
    private String display = "page";
    private String response_type = "token";
    private String access_token;
    private String email = "79686147598";//тут должен быть прописан email
    private String pass = "himitsudes42vk";//тут должен быть прописан пароль

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
    }

    public Set<String> getFriends(String id) throws URISyntaxException, HttpException, IOException, JSONException {
        String URL = new String("https://api.vk.com/method/friends.get?user_id="+id);
        //String URL = new String("https://api.vk.com/method/friends.get?user_id="+id+"&count=3&fields=first_name");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        JSONObject json = new JSONObject(responseString);
        String str = new String(json.getString("response"));
        String [] sarr = str.substring(1, str.length()-1).split(",");
        for (String s : sarr) {
            System.out.print(s+" ");
        }
        System.out.println();
        Set<String> set = new HashSet<>(Arrays.asList(sarr));

        return set;
    }

    public VKUser getUserInfo(String id) throws URISyntaxException, HttpException, IOException, JSONException {
        String URL = new String("https://api.vk.com/method/users.get?user_id="+id+"&fields=photo_50");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(new HttpGet(URL));
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        VKUser user = new VKUser();
        user.parseAndSetUser(responseString);
        return user;
    }
}
