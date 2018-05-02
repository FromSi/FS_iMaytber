package kz.sgq.fs_imaytber.infraestructure.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.FS_RC4;
import kz.sgq.fs_imaytber.util.GsonToTable;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public class FS_FirebaseMessagingService extends FirebaseMessagingService {
    private LocalDB localDB = new LocalDB();
    private SocketIMaytber socket = new SocketIMaytber();
    private String content;
    private String nick;
    private String avatar;
    private String bio;
    private String key;
    private String time;
    private int idmessages;
    private int idchats;
    private int iduser;
    private int iduser_1;
    private int iduser_2;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            init(remoteMessage);
        }
    }

    private void init(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().get("content") != null &&
                remoteMessage.getData().get("key") != null &&
                remoteMessage.getData().get("time") != null &&
                remoteMessage.getData().get("idmessages") != null &&
                remoteMessage.getData().get("idchats") != null &&
                remoteMessage.getData().get("iduser") != null) {
            content = remoteMessage.getData().get("content");
            key = remoteMessage.getData().get("key");
            time = remoteMessage.getData().get("time");
            idmessages = Integer.parseInt(remoteMessage.getData().get("idmessages"));
            idchats = Integer.parseInt(remoteMessage.getData().get("idchats"));
            iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            iduser_1 = Integer.parseInt(remoteMessage.getData().get("iduser_1"));
            iduser_2 = Integer.parseInt(remoteMessage.getData().get("iduser_2"));
            handlerUser();
        } else if (remoteMessage.getData().get("iduser") != null &&
                remoteMessage.getData().get("nick") != null) {
            iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            nick = remoteMessage.getData().get("nick");
            updateNick();
        } else if (remoteMessage.getData().get("iduser") != null &&
                remoteMessage.getData().get("avatar") != null) {
            iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            avatar = remoteMessage.getData().get("avatar");
            updateAvatar();
        } else if (remoteMessage.getData().get("iduser") != null &&
                remoteMessage.getData().get("bio") != null) {
            iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            bio = remoteMessage.getData().get("bio");
            updateBio();
        }
    }

    private void clear() {
        Log.d("TagTestServiseFCM", "clear");
        content = null;
        nick = null;
        avatar = null;
        key = null;
        time = null;
        idmessages = 0;
        idchats = 0;
        iduser = 0;
        iduser_1 = 0;
        iduser_2 = 0;
    }

    private void updateNick() {
        Log.d("TagTestServiseFCM", "updateNick");
        localDB.updateNickFriend(nick, iduser);
        clear();
    }

    private void updateAvatar() {
        Log.d("TagTestServiseFCM", "updateAvatar");
        localDB.updateAvatarFriend(avatar, iduser);
        clear();
    }

    private void updateBio() {
        Log.d("TagTestServiseFCM", "updateBio");
        localDB.updateBioFriend(bio, iduser);
        clear();
    }

    private void addMessage() {
        localDB.getIdChat(idchats)
                .subscribeOn(Schedulers.io())
                .subscribe(new MaybeObserver<TableChats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableChats chats) {
                        Log.d("TagTestServiseFCM", "addMessage onSuccess");
                        localDB.insertMessage(new TableMessages(idmessages, idchats,
                                iduser, new FS_RC4(key, content).start(), time));
                        clear();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("TagTestServiseFCM", "addMessage onComplete");
                        localDB.insertMessage(new TableMessages(idmessages, idchats,
                                iduser, new FS_RC4(key, content).start(), time));
                        localDB.insertChats(new TableChats(idchats, iduser_1,
                                iduser_2, key));
                        clear();

                    }
                });
    }

    private void handlerUser() {
        localDB.getUser(iduser_1)
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {
                        Log.d("TagTestServiseFCM", "handlerUser onSuccess");
                        addMessage();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("TagTestServiseFCM", "handlerUser onComplete");
                        downloadUser(iduser_1);
                    }
                });
    }

    private void downloadUser(int idUser) {
        socket.getProfile(idUser)
                .subscribe(new Observer<GETProfile>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GETProfile getProfile) {
                        Log.d("TagTestServiseFCM", "downloadUser");
                        TableUsers user = GsonToTable.tableUsers(getProfile);
                        localDB.insertUsers(user);
                        addMessage();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
