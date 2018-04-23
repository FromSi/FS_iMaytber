package kz.sgq.fs_imaytber.mvp.model.interfaces;

import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public interface LoginModel {
    int getMinSize();
    LocalDB getLocal();
    SocketIMaytber getSocket();
}
