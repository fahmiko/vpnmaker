package id.group1.vpnaccountmaker.model;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("photo")
    private String photo;
    private String action;

    public User(String id_user, String name, String username, String password, String photo, String action) {
        this.id_user = id_user;
        this.name = name;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.action = action;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}