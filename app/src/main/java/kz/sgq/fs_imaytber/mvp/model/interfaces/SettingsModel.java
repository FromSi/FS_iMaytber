package kz.sgq.fs_imaytber.mvp.model.interfaces;

import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public interface SettingsModel {
    LocalDB getLocal();

    SocketIMaytber getSocket();

    void setIdUser(int idUser);

    void setLoginUser(String loginUser);

    int getIdUser();

    String getLoginUser();
}
