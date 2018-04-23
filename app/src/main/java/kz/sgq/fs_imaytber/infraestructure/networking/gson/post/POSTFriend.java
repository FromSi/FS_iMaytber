package kz.sgq.fs_imaytber.infraestructure.networking.gson.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POSTFriend {
    @SerializedName("idfriend")
    @Expose
    private String idfriend;
    @SerializedName("iduser_2")
    @Expose
    private String iduser2;
    @SerializedName("iduser_1")
    @Expose
    private String iduser1;

    public String getIdfriend() {
        return idfriend;
    }

    public void setIdfriend(String idfriend) {
        this.idfriend = idfriend;
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