package kz.sgq.fs_imaytber.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableFriends {
    @PrimaryKey
    int idfriends;
    int iduser_1;
    int iduser_2;

    public TableFriends(int idfriends, int iduser_1, int iduser_2) {
        this.idfriends = idfriends;
        this.iduser_1 = iduser_1;
        this.iduser_2 = iduser_2;
    }

    public int getIdfriends() {
        return idfriends;
    }

    public void setIdfriends(int idfriends) {
        this.idfriends = idfriends;
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
}
