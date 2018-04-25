package kz.sgq.fs_imaytber.mvp.model.interfaces;

import java.util.List;

import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public interface DialogModel {
    LocalDB getLocal();
    SocketIMaytber getSocket();
    int getIdUser_1();
    int getIdUser_2();
    int getIdChat();
    List<Integer> getIdMessage();
    void addIdMessage(int idMessage);
    String getKey();
    void setKey(String key);
    String getNick();
    String getAvatar();
    void setIdChat(int id);
    void setNick(String nick);
    void setAvatar(String avatar);
}
