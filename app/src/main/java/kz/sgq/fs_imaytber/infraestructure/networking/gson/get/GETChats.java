package kz.sgq.fs_imaytber.infraestructure.networking.gson.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GETChats {
    @SerializedName("idchat")
    @Expose
    private String idchat;
    @SerializedName("iduser_2")
    @Expose
    private String iduser2;
    @SerializedName("iduser_1")
    @Expose
    private String iduser1;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("read")
    @Expose
    private String read;

    public String getIdchat() {
        return idchat;
    }

    public void setIdchat(String idchat) {
        this.idchat = idchat;
    }

    public String getIduser2() {
        return iduser2;
    }

    public void setIduser2(String iduser2) {
        this.iduser2 = iduser2;
    }

    public String getIduser1() {
        return iduser1;
    }

    public void setIduser1(String iduser1) {
        this.iduser1 = iduser1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
