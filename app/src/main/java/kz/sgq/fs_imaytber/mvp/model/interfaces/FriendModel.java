package kz.sgq.fs_imaytber.mvp.model.interfaces;

import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public interface FriendModel {
    LocalDB getLocal();
    SocketIMaytber getSocket();
    int getIdProfile();
}
