package kz.sgq.fs_imaytber.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import kz.sgq.fs_imaytber.room.table.TableProfile;

@Dao
public interface DaoProfile {
    @Insert
    void insert (TableProfile profile);

    @Query("SELECT * FROM `tableprofile`")
    Flowable<TableProfile> getProfile();

    @Query("UPDATE `tableprofile` SET `nick`=:nick")
    void putNick(String nick);

    @Query("UPDATE `tableprofile` SET `avatar`=:avatar")
    void putAvatar(String avatar);

    @Query("UPDATE `tableprofile` SET `bio`=:bio")
    void putBio(String bio);

    @Query("UPDATE `tableprofile` SET `password`=:password")
    void putPassword(String password);

    @Query("DELETE FROM `tableprofile`")
    void delete();
}
