package kz.sgq.fs_imaytber.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import kz.sgq.fs_imaytber.room.interfaces.DaoChats;
import kz.sgq.fs_imaytber.room.interfaces.DaoFriends;
import kz.sgq.fs_imaytber.room.interfaces.DaoMessage;
import kz.sgq.fs_imaytber.room.interfaces.DaoProfile;
import kz.sgq.fs_imaytber.room.interfaces.DaoUsers;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableProfile;
import kz.sgq.fs_imaytber.room.table.TableUsers;

@Database(entities = {TableChats.class, TableFriends.class,
        TableMessages.class, TableProfile.class,
        TableUsers.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoChats chats();

    public abstract DaoFriends friends();

    public abstract DaoMessage message();

    public abstract DaoProfile profile();

    public abstract DaoUsers users();
}
