package id.group1.vpnaccountmaker.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetAcc {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<Acc> result = new ArrayList<Acc>();
    @SerializedName("message")
    private String message;
    public GetAcc() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Acc> getResult() {
        return result;
    }

    public void setResult(List<Acc> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


