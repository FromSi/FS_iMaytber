package kz.sgq.fs_imaytber.mvp.model.interfaces;

import java.util.List;

import kz.sgq.fs_imaytber.util.HistoryZIP;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public interface HistoryModel {
    LocalDB getLocal();
    int getIdUser();
    void setIdUser(int idUser);
    List<Integer> getListIdUser_2();
    void clearListIdUser_2();
    void clearListIdChat();
    void addListIdUser_2(int idUser);
    void addIdChatList(int idChat);
    List<Integer> getIdChatList();
}
