package id.group1.vpnaccountmaker.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetServer {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Server> result = new ArrayList<Server>();
    @SerializedName("message")
    private String message;
    public GetServer() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Server> getResult() {
        return result;
    }

    public void setResult(List<Server> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


