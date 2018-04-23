package kz.sgq.fs_imaytber.mvp.model;

import kz.sgq.fs_imaytber.mvp.model.interfaces.MainModel;
import kz.sgq.fs_imaytber.util.LocalDB;

public class MainModelImpl implements MainModel {
    private LocalDB localDB;

    public MainModelImpl() {
        localDB = new LocalDB();
    }

    @Override
    public LocalDB getLocal() {
        return localDB;
    }
}
