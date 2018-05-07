package kz.sgq.fs_imaytber.mvp.model;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.mvp.model.interfaces.MainModel;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.GsonToTable;
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
    public void initUsers(int idUser) {
        localDB.getChats()
                .subscribe(new MaybeObserver<List<TableChats>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<TableChats> chatsList) {
                        for (int i = 0; i < chatsList.size(); i++) {
                            if (chatsList.get(i).getIduser_1() != idUser)
                                checkFriendsUser(chatsList.get(i).getIduser_1());
                            else
                                checkFriendsUser(chatsList.get(i).getIduser_2());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void checkFriendsUser(int idUser) {
        localDB.getFriend(idUser)
                .subscribe(new MaybeObserver<TableFriends>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableFriends tableFriends) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        checkUser(idUser);
                    }
                });
    }

    private void checkUser(int idUser) {
        localDB.getUser(idUser)
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        downloadUser(idUser);
                    }
                });
    }

    private void downloadUser(int idUser) {
        socket.getProfile(idUser)
                .subscribe(new Observer<GETProfile>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GETProfile getProfile) {
                        localDB.insertUsers(GsonToTable.tableUsers(getProfile));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
