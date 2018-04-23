package kz.sgq.fs_imaytber.infraestructure.networking.gson.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POSTUser {
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("iduser")
    @Expose
    private String iduser;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("token")
    @Expose
    private String token;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}