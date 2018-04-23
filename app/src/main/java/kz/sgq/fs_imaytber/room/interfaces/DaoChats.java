package kz.sgq.fs_imaytber.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import kz.sgq.fs_imaytber.room.table.TableChats;

@Dao
public interface DaoChats {
    @Insert
    void insert(TableChats... chats);

    @Insert
    void insertAll(List<TableChats> chatsList);

    @Query("SELECT * FROM `tablechats`")
    Flowable<List<TableChats>> getChats();

    @Query("DELETE FROM `tablechats`")
    void delete();
}
