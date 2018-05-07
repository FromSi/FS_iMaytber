package kz.sgq.fs_imaytber.mvp.model.interfaces;

import java.util.List;

import kz.sgq.fs_imaytber.util.LocalDB;

public interface HistoryModel {
    LocalDB getLocal();

    int getIdUser();

    void setIdUser(int idUser);

    List<Integer> getListIdUser_2();

    List<Integer> getReadList();

    void clearListIdUser_2();

    void clearListIdChat();

    void clearReadList();

    void addListIdUser_2(int idUser);

    void addIdChatList(int idChat);

    void addReadList(int read);

    List<Integer> getIdChatList();
}
