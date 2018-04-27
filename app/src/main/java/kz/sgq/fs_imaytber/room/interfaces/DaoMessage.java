package kz.sgq.fs_imaytber.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import kz.sgq.fs_imaytber.room.table.TableMessages;

@Dao
public interface DaoMessage {
    @Insert
    void insert (TableMessages... messages);

    @Insert
    void insertAll(List<TableMessages> messagesList);

    @Query("SELECT * FROM `tablemessages` WHERE `idchats`=:idchat")
    Flowable<List<TableMessages>> getMessages(int idchat);

    @Query("SELECT * FROM `tablemessages` ORDER BY `idmessages` DESC LIMIT 1")
    Flowable<TableMessages> getMessage();

    @Query("SELECT * FROM `tablemessages` WHERE `idchats`=:idchat ORDER BY `idmessages` DESC LIMIT 1")
    Maybe<List<TableMessages>> getDialogs(int idchat);

    @Query("SELECT * FROM `tablemessages` WHERE `idchats`=:idChat ORDER BY `idmessages` DESC LIMIT 1")
    Maybe<TableMessages> getDialog(int idChat);

    @Query("DELETE FROM `tablemessages`")
    void delete();
}
