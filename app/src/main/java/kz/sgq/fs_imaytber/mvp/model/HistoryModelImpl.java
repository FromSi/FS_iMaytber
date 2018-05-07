package kz.sgq.fs_imaytber.mvp.model;

import java.util.ArrayList;
import java.util.List;

import kz.sgq.fs_imaytber.mvp.model.interfaces.HistoryModel;
import kz.sgq.fs_imaytber.util.LocalDB;

public class HistoryModelImpl implements HistoryModel {
    private LocalDB localDB;
    private int idUser;
    private List<Integer> idListUser_2;
    private List<Integer> idChat;
    private List<Integer> readList;

    public HistoryModelImpl() {
        localDB = new LocalDB();
        idListUser_2 = new ArrayList<>();
        idChat = new ArrayList<>();
        readList = new ArrayList<>();
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
    public List<Integer> getReadList() {
        return readList;
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
    public void clearReadList() {
        readList.clear();
    }


    @Override
    public void addListIdUser_2(int idUser) {
        this.idListUser_2.add(idUser);
    }

    @Override
    public void addIdChatList(int idChat) {
        this.idChat.add(idChat);
    }

    @Override
    public void addReadList(int read) {
        this.readList.add(read);
    }


    @Override
    public List<Integer> getIdChatList() {
        return idChat;
    }

}
