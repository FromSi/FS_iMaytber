package kz.sgq.fs_imaytber.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import kz.sgq.fs_imaytber.room.table.TableUsers;

@Dao
public interface DaoUsers {
    @Insert
    void insert(TableUsers... users);

    @Insert
    void insertAll(List<TableUsers> usersList);

    @Query("SELECT * FROM `tableusers` WHERE `idusers`=:idusers")
    Maybe<List<TableUsers>> getUser(int idusers);

    @Query("DELETE FROM `tableusers`")
    void delete();
}
