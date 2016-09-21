package main.apivk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shi on 21.09.16.
 */
public class VkUser {
    private String firstName;
    private String lastName;
    private String userId;
    private String photoURL;

    public VkUser(String source) {
        parseAndSetUser(source);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getUserId() {
        return userId;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    void parseAndSetUser(String source) {
        try {
            JSONObject jo = new JSONObject(source);
            setFirstName(jo.getString("first_name"));
            setLastName(jo.getString("last_name"));
            setUserId(jo.getString("uid"));
            setPhotoURL(jo.getString("photo_100").replaceAll("\\\\", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "VKUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId='" + userId + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkUser)) return false;

        VkUser vkUser = (VkUser) o;

        return userId.equals(vkUser.userId);

    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
