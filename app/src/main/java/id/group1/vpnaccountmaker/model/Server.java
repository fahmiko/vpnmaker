package id.group1.vpnaccountmaker.model;
import com.google.gson.annotations.SerializedName;

public class Server {
    @SerializedName("id_server")
    private String id_server;
    @SerializedName("name_server")
    private String name_server;
    @SerializedName("location")
    private String location;
    @SerializedName("acc_remaining")
    private String acc_remaining;
    @SerializedName("flag_image")
    private String flag_image;
    private String action;


    public Server(String idServer, String name_server, String location, String acc_remaining, String flag_image, String action) {
        this.id_server = idServer;
        this.name_server = name_server;
        this.location = location;
        this.acc_remaining = acc_remaining;
        this.flag_image = flag_image;
        this.action = action;
    }

    public String getIdServer() {
        return id_server;
    }

    public void setIdServer(String idServer) {
        this.id_server = idServer;
    }

    public String getName_server() {
        return name_server;
    }

    public void setName_server(String name_server) {
        this.name_server = name_server;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAcc_remaining() {
        return acc_remaining;
    }

    public void setAcc_remaining(String acc_remaining) {
        this.acc_remaining = acc_remaining;
    }

    public String getFlag_image() {
        return flag_image;
    }

    public void setFlag_image(String flag_image) {
        this.flag_image = flag_image;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
