package kz.sgq.fs_imaytber.infraestructure.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.ui.activity.BaseActivity;
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
    private int read;

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
                remoteMessage.getData().get("nick") != null &&
                remoteMessage.getData().get("idmessages") != null &&
                remoteMessage.getData().get("idchats") != null &&
                remoteMessage.getData().get("iduser") != null) {
            content = remoteMessage.getData().get("content");
            key = remoteMessage.getData().get("key");
            time = remoteMessage.getData().get("time");
            nick = remoteMessage.getData().get("nick");
            idmessages = Integer.parseInt(remoteMessage.getData().get("idmessages"));
            read = Integer.parseInt(remoteMessage.getData().get("read"));
            idchats = Integer.parseInt(remoteMessage.getData().get("idchats"));
            iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            iduser_1 = Integer.parseInt(remoteMessage.getData().get("iduser_1"));
            iduser_2 = Integer.parseInt(remoteMessage.getData().get("iduser_2"));

            if (PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("notif", true))
                if (iduser != iduser_1) {
                    if (PreferenceManager.getDefaultSharedPreferences(this)
                            .getInt("notifId", 0) != iduser_1)
                        handlerNotif(iduser_1, nick, new FS_RC4(key, content).start());
                }else {
                    if (PreferenceManager.getDefaultSharedPreferences(this)
                            .getInt("notifId", 0) != iduser_2)
                        handlerNotif(iduser_2, nick, new FS_RC4(key, content).start());
                }
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

    private void showNotification(String nick, String content) {
        Intent resultIntent = new Intent(this, BaseActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle(nick)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentIntent(resultPendingIntent);


        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void clear() {
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
        localDB.updateNickFriend(nick, iduser);
        clear();
    }

    private void updateAvatar() {
        localDB.updateAvatarFriend(avatar, iduser);
        clear();
    }

    private void updateBio() {
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
                        localDB.insertMessage(new TableMessages(idmessages, idchats,
                                iduser, new FS_RC4(key, content).start(), time));
                        localDB.updateChatRead(read, chats.getIdchats());
                        clear();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        localDB.insertMessage(new TableMessages(idmessages, idchats,
                                iduser, new FS_RC4(key, content).start(), time));
                        localDB.insertChats(new TableChats(idchats, iduser_1,
                                iduser_2, key, read));
                        clear();
                    }
                });
    }

    private void handlerNotif(int idUser, String nick, String content) {
        localDB.getUser(idUser)
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {
                        if (tableUsers.isNotif())
                            showNotification(nick, content);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        showNotification(nick, content);
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
                        addMessage();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
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
