package kz.sgq.fs_imaytber.infraestructure.networking.gson.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GETFriend {
    @SerializedName("idfriends")
    @Expose
    private String idfriends;
    @SerializedName("iduser_2")
    @Expose
    private String iduser2;
    @SerializedName("iduser_1")
    @Expose
    private String iduser1;

    public String getIdfriends() {
        return idfriends;
    }

    public void setIdfriends(String idfriends) {
        this.idfriends = idfriends;
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

}