package kz.sgq.fs_imaytber.mvp.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kz.sgq.fs_imaytber.mvp.model.interfaces.DialogModel;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public class DialogModelImpl implements DialogModel {
    private LocalDB localDB;
    private SocketIMaytber socket;
    private int idUser_1;
    private int idUser_2;
    private int idChat = 0;
    private List<Integer> idMessageList;
    private String key;
    private String avatar;
    private String nick;

    public DialogModelImpl(int idUser_1,int idUser_2) {
        localDB = new LocalDB();
        socket = new SocketIMaytber();
        this.idUser_1 = idUser_1;
        this.idUser_2 = idUser_2;
        idMessageList = new ArrayList<>();
        initKey();
    }

    private void initKey(){
        localDB.getChatKey(idUser_2)
                .subscribe(new MaybeObserver<TableChats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableChats chats) {
                        key = chats.getKey();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public LocalDB getLocal() {
        return localDB;
    }

    @Override
    public SocketIMaytber getSocket() {
        return socket;
    }

    @Override
    public int getIdUser_1() {
        return idUser_1;
    }

    @Override
    public int getIdUser_2() {
        return idUser_2;
    }

    @Override
    public int getIdChat() {
        return idChat;
    }

    @Override
    public List<Integer> getIdMessage() {
        return idMessageList;
    }

    @Override
    public void addIdMessage(int idMessage) {
        idMessageList.add(idMessage);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key= key;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void setIdChat(int id) {
        idChat = id;
    }

    @Override
    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
