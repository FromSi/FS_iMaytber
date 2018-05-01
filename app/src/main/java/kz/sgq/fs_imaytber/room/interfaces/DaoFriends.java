package kz.sgq.fs_imaytber.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import kz.sgq.fs_imaytber.room.table.TableFriends;

@Dao
public interface DaoFriends {
    @Insert
    void insert(TableFriends... friends);

    @Insert
    void insertAll(List<TableFriends> friendsList);

    @Query("SELECT * FROM `tablefriends`")
    Flowable<List<TableFriends>> getFriends();

    @Query("SELECT * FROM `tablefriends` ORDER BY `idfriends` LIMIT 1")
    Maybe<TableFriends> getFriend();

    @Query("DELETE FROM `tablefriends`")
    void delete();
}
