package main.apivk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shi on 21.09.16.
 */
public class VkUser {
    private final String firstName;
    private final String lastName;
    private final String userId;
    private final String photoURL;

    public static VkUser parse(String jsonSource) {
        return new VkUser(jsonSource);
    }

    private VkUser(String source) {
        try {
            JSONObject jo = new JSONObject(source);
            firstName = jo.getString("first_name");
            lastName = jo.getString("last_name");
            userId = jo.getString("uid");
            photoURL = jo.getString("photo_100").replaceAll("\\\\", "");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhotoURL() {
        return photoURL;
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
