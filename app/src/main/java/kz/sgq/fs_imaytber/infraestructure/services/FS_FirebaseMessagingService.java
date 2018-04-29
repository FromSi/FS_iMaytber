package kz.sgq.fs_imaytber.infraestructure.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
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
    private String key;
    private int idmessages;
    private int idchats;
    private int iduser;
    private int iduser_1;
    private int iduser_2;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            content = remoteMessage.getData().get("content");
            nick = remoteMessage.getData().get("nick");
            key = remoteMessage.getData().get("key");
            idmessages = Integer.parseInt(remoteMessage.getData().get("idmessages"));
            idchats = Integer.parseInt(remoteMessage.getData().get("idchats"));
            iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            iduser_1 = Integer.parseInt(remoteMessage.getData().get("iduser_1"));
            iduser_2 = Integer.parseInt(remoteMessage.getData().get("iduser_2"));
            handlerUser();
        }
    }

    private void addMessage(){
        localDB.getIdChat(idchats)
                .subscribeOn(Schedulers.io())
                .subscribe(new MaybeObserver<TableChats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableChats chats) {
                        localDB.insertMessage(new TableMessages(idmessages, idchats,
                                iduser, new FS_RC4(key, content).start()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        localDB.insertMessage(new TableMessages(idmessages, idchats,
                                iduser, new FS_RC4(key, content).start()));
                        localDB.insertChats(new TableChats(idchats, iduser_1,
                                iduser_2, key));

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
