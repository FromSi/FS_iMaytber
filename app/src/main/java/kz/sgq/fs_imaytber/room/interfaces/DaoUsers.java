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

    @Query("SELECT * FROM `tableusers` WHERE `idusers`=:idUser")
    Maybe<TableUsers> getUser(int idUser);

    @Query("UPDATE `tableusers` SET `nick`=:nick WHERE `idusers`=:idUser")
    void putNick(String nick, int idUser);

    @Query("UPDATE `tableusers` SET `avatar`=:avatar WHERE `idusers`=:idUser")
    void putAvatar(String avatar, int idUser);

    @Query("UPDATE `tableusers` SET `bio`=:bio WHERE `idusers`=:idUser")
    void putBio(String bio, int idUser);

    @Query("DELETE FROM `tableusers`")
    void delete();
}
