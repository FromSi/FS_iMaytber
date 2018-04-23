package kz.sgq.fs_imaytber.util;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kz.sgq.fs_imaytber.application.App;
import kz.sgq.fs_imaytber.room.AppDatabase;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableProfile;
import kz.sgq.fs_imaytber.room.table.TableUsers;

public class LocalDB {
    private AppDatabase database;

    public LocalDB() {
        database = App.getInstance().getDatabase();
    }

    public void insertChats(List<TableChats> chatsList) {
        Completable.fromAction(() -> database.chats().insertAll(chatsList))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertChats(TableChats chats) {
        Completable.fromAction(() -> database.chats().insert(chats))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertFriends(List<TableFriends> friendsList) {
        Completable.fromAction(() -> database.friends().insertAll(friendsList))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertFriends(TableFriends friends) {
        Completable.fromAction(() -> database.friends().insert(friends))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertMessage(List<TableMessages> messagesList) {
        Completable.fromAction(() -> database.message().insertAll(messagesList))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertMessage(TableMessages messages) {
        Completable.fromAction(() -> database.message().insert(messages))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertProfile(final TableProfile profile) {
        Completable.fromAction(() -> database.profile().insert(profile))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertUsers(List<TableUsers> usersList) {
        Completable.fromAction(() -> database.users().insertAll(usersList))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertUsers(TableUsers users) {
        Completable.fromAction(() -> database.users().insert(users))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteChats(){
        Completable.fromAction(() -> database.chats().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteFriends(){
        Completable.fromAction(() -> database.friends().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteMessage(){
        Completable.fromAction(() -> database.message().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }


    public void deleteProfile(){
        Completable.fromAction(() -> database.profile().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteUsers(){
        Completable.fromAction(() -> database.users().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateAvatar(String avatar){
        Completable.fromAction(() -> database.profile().putAvatar(avatar))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateNick(String nick){
        Completable.fromAction(() -> database.profile().putNick(nick))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updatePassword(String password){
        Completable.fromAction(() -> database.profile().putAvatar(password))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteAll(){
        deleteChats();
        deleteFriends();
        deleteMessage();
        deleteProfile();
        deleteUsers();
    }

    public Flowable<List<TableChats>> getChats(){
        return database.chats()
                .getChats()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<TableFriends>> getFriends(){
        return database.friends()
                .getFriends()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<TableProfile> getProfile(){
        return database.profile()
                .getProfile()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableUsers> getUser(int idusers){
        return database.users()
                .getUser(idusers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<TableMessages>> getMessages(int idchat){
        return database.message()
                .getMessages(idchat)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<List<TableMessages>> getDialogs(int idchat){
        return database.message()
                .getDialogs(idchat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
