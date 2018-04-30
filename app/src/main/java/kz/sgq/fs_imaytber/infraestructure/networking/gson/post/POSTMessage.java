package kz.sgq.fs_imaytber.infraestructure.networking.gson.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POSTMessage {
    @SerializedName("iduser")
    @Expose
    private String iduser;
    @SerializedName("idchat")
    @Expose
    private String idchat;
    @SerializedName("idmessage")
    @Expose
    private String idmessage;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("time")
    @Expose
    private String time;

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdchat() {
        return idchat;
    }

    public void setIdchat(String idchat) {
        this.idchat = idchat;
    }

    public String getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(String idmessage) {
        this.idmessage = idmessage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
