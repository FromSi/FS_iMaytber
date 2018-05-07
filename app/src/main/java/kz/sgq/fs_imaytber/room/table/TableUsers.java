package kz.sgq.fs_imaytber.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableUsers {
    @PrimaryKey
    int idusers;
    String avatar;
    String nick;
    String bio;
    boolean notif;

    public TableUsers(int idusers, String avatar, String nick, String bio, boolean notif) {
        this.idusers = idusers;
        this.avatar = avatar;
        this.nick = nick;
        this.bio = bio;
        this.notif = notif;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isNotif() {
        return notif;
    }

    public void setNotif(boolean notif) {
        this.notif = notif;
    }
}
