package kz.sgq.fs_imaytber.util;

import android.util.Log;

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

    public void deleteChats() {
        Completable.fromAction(() -> database.chats().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteFriends() {
        Completable.fromAction(() -> database.friends().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteMessage() {
        Completable.fromAction(() -> database.message().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteMessageId(int idMessage) {
        Completable.fromAction(() -> database.message().deleteMessage(idMessage))
                .subscribeOn(Schedulers.io())
                .subscribe();
        Log.d("MyLilili", "deleteMessageId");
    }


    public void deleteProfile() {
        Completable.fromAction(() -> database.profile().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteUsers() {
        Completable.fromAction(() -> database.users().delete())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateAvatar(String avatar) {
        Completable.fromAction(() -> database.profile().putAvatar(avatar))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateKey(String key, int idUser) {
        Completable.fromAction(() -> database.chats().putKey(key, idUser))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateNotif(boolean notif, int idUser) {
        Completable.fromAction(() -> database.users().putNotif(notif, idUser))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateNick(String nick) {
        Completable.fromAction(() -> database.profile().putNick(nick))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateNickFriend(String nick, int idUser) {
        Completable.fromAction(() -> database.users().putNick(nick, idUser))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateBioFriend(String bio, int idUser) {
        Completable.fromAction(() -> database.users().putBio(bio, idUser))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateBio(String bio) {
        Completable.fromAction(() -> database.profile().putBio(bio))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateAvatarFriend(String avatar, int idUser) {
        Completable.fromAction(() -> database.users().putAvatar(avatar, idUser))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteFriend(int idFriends) {
        Completable.fromAction(() -> database.friends().deleteFriend(idFriends))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateChatRead(int read, int idChat) {
        Completable.fromAction(() -> database.chats().putRead(read, idChat))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updatePassword(String password) {
        Completable.fromAction(() -> database.profile().putAvatar(password))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteAll() {
        deleteChats();
        deleteFriends();
        deleteMessage();
        deleteProfile();
        deleteUsers();
    }

    public Maybe<List<TableChats>> getChats() {
        return database.chats()
                .getChats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableChats> getChat() {
        return database.chats()
                .getChat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableFriends> getFriend() {
        return database.friends()
                .getFriend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableFriends> getFriend(int idUser) {
        return database.friends()
                .getFriend(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<TableFriends>> getFriends() {
        return database.friends()
                .getFriends()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<TableChats> getIdChatPref(int idUser) {
        return database.chats()
                .getIdChatPref(idUser)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<TableProfile> getProfile() {
        return database.profile()
                .getProfile()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableUsers> getUser(int idUser) {
        return database.users()
                .getUser(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableMessages> getDialog(int id) {
        return database.message()
                .getDialog(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableChats> getIdChat(int idChat) {
        return database.chats()
                .getIdChat(idChat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<TableChats> getChatKey(int idUser) {
        return database.chats()
                .getChatKey(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<TableMessages>> getMessages(int idChat) {
        return database.message()
                .getMessages(idChat)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<TableMessages> getMessage() {
        return database.message()
                .getMessage()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<List<TableMessages>> getDialogs(int idChat) {
        return database.message()
                .getDialogs(idChat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
