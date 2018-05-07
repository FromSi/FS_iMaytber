package kz.sgq.fs_imaytber.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableChats {
    @PrimaryKey
    int idchats;
    int iduser_1;
    int iduser_2;
    String key;
    int read;

    public TableChats(int idchats, int iduser_1, int iduser_2, String key, int read) {
        this.idchats = idchats;
        this.iduser_1 = iduser_1;
        this.iduser_2 = iduser_2;
        this.key = key;
        this.read = read;
    }

    public int getIdchats() {
        return idchats;
    }

    public void setIdchats(int idchats) {
        this.idchats = idchats;
    }

    public int getIduser_1() {
        return iduser_1;
    }

    public void setIduser_1(int iduser_1) {
        this.iduser_1 = iduser_1;
    }

    public int getIduser_2() {
        return iduser_2;
    }

    public void setIduser_2(int iduser_2) {
        this.iduser_2 = iduser_2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
