package kz.sgq.fs_imaytber.mvp.model;

import java.util.ArrayList;
import java.util.List;

import kz.sgq.fs_imaytber.mvp.model.interfaces.HistoryModel;
import kz.sgq.fs_imaytber.util.HistoryZIP;
import kz.sgq.fs_imaytber.util.LocalDB;

public class HistoryModelImpl implements HistoryModel {
    private LocalDB localDB;
    private int idUser;
    private List<Integer> idListUser_2;
    private List<Integer> idChat;

    public HistoryModelImpl() {
        localDB = new LocalDB();
        idListUser_2 = new ArrayList<>();
        idChat = new ArrayList<>();
    }

    @Override
    public LocalDB getLocal() {
        return localDB;
    }

    @Override
    public int getIdUser() {
        return idUser;
    }

    @Override
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public List<Integer> getListIdUser_2() {
        return idListUser_2;
    }

    @Override
    public void clearListIdUser_2() {
        idListUser_2.clear();
    }

    @Override
    public void clearListIdChat() {
        idChat.clear();
    }


    @Override
    public void addListIdUser_2(int idUser) {
        idListUser_2.add(idUser);
    }

    @Override
    public void addIdChatList(int idChat) {
        this.idChat.add(idChat);
    }


    @Override
    public List<Integer> getIdChatList() {
        return idChat;
    }

}
