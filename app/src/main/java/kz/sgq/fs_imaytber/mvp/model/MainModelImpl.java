package kz.sgq.fs_imaytber.mvp.model;

import kz.sgq.fs_imaytber.mvp.model.interfaces.MainModel;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public class MainModelImpl implements MainModel {
    private LocalDB localDB;
    private SocketIMaytber socket;
    private int idUser;

    public MainModelImpl() {
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
    public int getIdUser() {
        return idUser;
    }
}
