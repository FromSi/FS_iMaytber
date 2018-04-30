package kz.sgq.fs_imaytber.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableMessages {
    @PrimaryKey
    int idmessages;
    int idchats;
    int iduser;
    String content;
    String time;

    public TableMessages(int idmessages, int idchats, int iduser, String content, String time) {
        this.idmessages = idmessages;
        this.idchats = idchats;
        this.iduser = iduser;
        this.content = content;
        this.time = time;
    }

    public int getIdmessages() {
        return idmessages;
    }

    public void setIdmessages(int idmessages) {
        this.idmessages = idmessages;
    }

    public int getIdchats() {
        return idchats;
    }

    public void setIdchats(int idchats) {
        this.idchats = idchats;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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
