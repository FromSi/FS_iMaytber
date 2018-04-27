package kz.sgq.fs_imaytber.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import kz.sgq.fs_imaytber.room.table.TableChats;

@Dao
public interface DaoChats {
    @Insert
    void insert(TableChats... chats);

    @Insert
    void insertAll(List<TableChats> chatsList);

    @Query("SELECT * FROM `tablechats`")
    Maybe<List<TableChats>> getChats();

    @Query("SELECT * FROM `tablechats` WHERE `iduser_1`=:idUser OR `iduser_2`=:idUser")
    Maybe<TableChats> getChatKey(int idUser);

    @Query("SELECT * FROM `tablechats` WHERE `idchats`=:idChat")
    Maybe<TableChats> getIdChat(int idChat);

    @Query("UPDATE `tablechats` SET `key`=:key WHERE `iduser_1`=:idUser OR `iduser_2`=:idUser")
    void putKey(String key, int idUser);

    @Query("DELETE FROM `tablechats`")
    void delete();
}
