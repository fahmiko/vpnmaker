package id.group1.vpnaccountmaker.model;

public class ServerModel {
    private int id;
    private String name_server;
    private String location;
    private int acc_remaining;

    public ServerModel(int id_server, String name_server, String location, int acc_remaining) {
        this.id = id_server;
        this.name_server = name_server;
        this.location = location;
        this.acc_remaining = acc_remaining;
    }

    public int getId() {
        return id;
    }

    public String getName_server() {
        return name_server;
    }

    public String getLocation() {
        return location;
    }

    public int getAcc_remaining() {
        return acc_remaining;
    }
}
