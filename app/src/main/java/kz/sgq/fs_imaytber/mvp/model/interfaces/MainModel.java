package kz.sgq.fs_imaytber.mvp.model.interfaces;

import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public interface MainModel {
    LocalDB getLocal();

    SocketIMaytber getSocket();

    void setIdUser(int idUser);

    void initUsers(int idUser);

    int getIdUser();
}
