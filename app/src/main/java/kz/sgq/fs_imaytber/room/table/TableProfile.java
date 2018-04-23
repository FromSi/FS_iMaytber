package kz.sgq.fs_imaytber.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableProfile {
    @PrimaryKey
    int iduser;
    String avatar;
    String nick;
    String login;
    String password;

    public TableProfile(int iduser, String avatar, String nick, String login, String password) {
        this.iduser = iduser;
        this.avatar = avatar;
        this.nick = nick;
        this.login = login;
        this.password = password;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
