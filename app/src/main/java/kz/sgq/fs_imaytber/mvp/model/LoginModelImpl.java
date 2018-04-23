package kz.sgq.fs_imaytber.mvp.model;

import kz.sgq.fs_imaytber.mvp.model.interfaces.LoginModel;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public class LoginModelImpl implements LoginModel {

    private LocalDB localDB;
    private SocketIMaytber socket;

    public LoginModelImpl() {
        localDB = new LocalDB();
        socket = new SocketIMaytber();
    }

    @Override
    public int getMinSize() {
        return 5;
    }

    @Override
    public LocalDB getLocal() {
        return localDB;
    }

    @Override
    public SocketIMaytber getSocket() {
        return socket;
    }
}
