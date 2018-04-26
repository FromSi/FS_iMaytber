package kz.sgq.fs_imaytber.infraestructure.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.util.FS_RC4;
import kz.sgq.fs_imaytber.util.LocalDB;

public class FS_FirebaseMessagingService extends FirebaseMessagingService {
    private boolean bool = true;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            LocalDB localDB = new LocalDB();
            String content = remoteMessage.getData().get("content");
            String nick = remoteMessage.getData().get("nick");
            String key = remoteMessage.getData().get("key");
            int idmessages = Integer.parseInt(remoteMessage.getData().get("idmessages"));
            int idchats = Integer.parseInt(remoteMessage.getData().get("idchats"));
            int iduser = Integer.parseInt(remoteMessage.getData().get("iduser"));
            int iduser_1 = Integer.parseInt(remoteMessage.getData().get("iduser_1"));
            int iduser_2 = Integer.parseInt(remoteMessage.getData().get("iduser_2"));

            localDB.getIdChat(idchats)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new MaybeObserver<TableChats>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(TableChats chats) {

                            bool = false;
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            if (bool){
                localDB.insertMessage(new TableMessages(idmessages, idchats,
                        iduser, new FS_RC4(key, content).start()));
                localDB.insertChats(new TableChats(idchats, iduser_1,
                        iduser_2, key));
            }else {
                localDB.insertMessage(new TableMessages(idmessages, idchats,
                        iduser, new FS_RC4(key, content).start()));
            }
        }
    }
}
