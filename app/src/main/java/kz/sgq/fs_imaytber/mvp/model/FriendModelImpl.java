package kz.sgq.fs_imaytber.mvp.model;

import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.mvp.model.interfaces.FriendModel;
import kz.sgq.fs_imaytber.room.table.TableProfile;
import kz.sgq.fs_imaytber.util.LocalDB;
import kz.sgq.fs_imaytber.util.SocketIMaytber;

public class FriendModelImpl implements FriendModel {
    private LocalDB local;
    private SocketIMaytber socket;
    private int idProfile;

    public FriendModelImpl() {
        local = new LocalDB();
        socket = new SocketIMaytber();
        init();
    }

    private void init() {
        local.getProfile()
                .subscribe(new DisposableSubscriber<TableProfile>() {
                    @Override
                    public void onNext(TableProfile profile) {
                        idProfile = profile.getIduser();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public LocalDB getLocal() {
        return local;
    }

    @Override
    public SocketIMaytber getSocket() {
        return socket;
    }

    @Override
    public int getIdProfile() {
        return idProfile;
    }
}
