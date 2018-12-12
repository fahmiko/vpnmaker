package id.group1.vpnaccountmaker.model;

import com.google.gson.annotations.SerializedName;

public class Acc {
    @SerializedName("id")
    private String id;
    @SerializedName("name_server")
    private String server;
    @SerializedName("name")
    private String user;
    @SerializedName("active")
    private String active;
    private String action;

    public Acc(String id, String server, String user, String active, String action) {
        this.id = id;
        this.server = server;
        this.user = user;
        this.active = active;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}