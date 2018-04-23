package kz.sgq.fs_imaytber.mvp.model;

import kz.sgq.fs_imaytber.mvp.model.interfaces.SettingsModel;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public class SettingsModelImpl implements SettingsModel {
    private LocalDB localDB;
    private SocketIMaytber socket;

    private int idUser;
    private String loginUser;

    public SettingsModelImpl() {
        localDB = new LocalDB();
        socket = new SocketIMaytber();
    }


    @Override
    public LocalDB getLocal() {
        return localDB;
    }

    @Override
    public SocketIMaytber getSocket() {
        return socket;
    }

    @Override
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public int getIdUser() {
        return idUser;
    }

    @Override
    public String getLoginUser() {
        return loginUser;
    }
}
