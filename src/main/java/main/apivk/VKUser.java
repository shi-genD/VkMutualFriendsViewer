package main.apivk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shi on 16.09.16.
 */
public class VKUser {

    private String firstName;
    private String lastName;
    private String photoURL;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void parseAndSetUser(String source) throws JSONException {
        JSONObject js = new JSONObject(source);
        JSONArray response = js.getJSONArray("response");
        JSONObject jo = response.getJSONObject(0);
        setFirstName(jo.getString("first_name"));
        setLastName(jo.getString("last_name"));
        setPhotoURL(jo.getString("photo_50").replaceAll("\\\\", ""));
    }

    @Override
    public String toString() {
        return "VKUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
