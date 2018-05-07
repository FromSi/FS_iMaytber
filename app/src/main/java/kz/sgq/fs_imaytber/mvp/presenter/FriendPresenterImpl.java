package kz.sgq.fs_imaytber.mvp.presenter;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTFriend;
import kz.sgq.fs_imaytber.mvp.model.FriendModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.FriendModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.FriendPresenter;
import kz.sgq.fs_imaytber.mvp.view.FriendView;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.GsonToTable;

public class FriendPresenterImpl implements FriendPresenter {
    private FriendView view;
    private FriendModel model;
    private CompositeDisposable composite;

    public FriendPresenterImpl(FriendView view) {
        this.view = view;
        composite = new CompositeDisposable();
        model = new FriendModelImpl();
        checkItem();
    }

    private void checkItem() {
        model.getLocal()
                .getFriend()
                .subscribe(new MaybeObserver<TableFriends>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableFriends tableFriends) {
                        init();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        init();
                        view.showNullItem();
                    }
                });
    }

    void init() {
        composite.add(
                model.getLocal()
                        .getFriends()
                        .subscribeWith(new DisposableSubscriber<List<TableFriends>>() {
                            @Override
                            public void onNext(List<TableFriends> friendsList) {
                                if (friendsList.size() != 0) {
                                    List<Integer> list = view.getIdUsers();
                                    if (list.size() < friendsList.size()) {
                                        for (int i = 0; i < friendsList.size(); i++) {
                                            boolean bool = true;
                                            for (int j = 0; j < list.size(); j++) {
                                                if (friendsList.get(i).getIduser_2() ==
                                                        list.get(j))
                                                    bool = false;
                                            }
                                            if (bool)
                                                handlerUser(friendsList.get(i).getIduser_2());
                                        }
                                    } else {
                                        for (int i = 0; i < list.size(); i++) {
                                            if (checkItemDelete(list.get(i), friendsList))
                                                view.deleteFriend(list.get(i));
                                        }
                                    }
                                } else {
                                    if (view.getIdUsers().size() != 0) {
                                        view.deleteFriend(view.getIdUsers().get(0));
                                        view.showNullItem();
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {
                            }
                        }));
    }

    private boolean checkItemDelete(int idUser, List<TableFriends> friendsList) {
        for (int i = 0; i < friendsList.size(); i++) {
            if (idUser == friendsList.get(i).getIduser_2())
                return false;
        }
        return true;
    }

    private void handlerUser(int idUser) {
        composite.add(
                model.getLocal()
                        .getUser(idUser)
                        .subscribeWith(new DisposableMaybeObserver<TableUsers>() {
                            @Override
                            public void onSuccess(TableUsers tableUsers) {
                                view.addFriend(tableUsers);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                downloadUser(idUser);
                            }
                        }));
    }

    private void downloadUser(int idUser) {
        model.getSocket()
                .getProfile(idUser)
                .subscribe(new Observer<GETProfile>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GETProfile getProfile) {
                        TableUsers user = GsonToTable.tableUsers(getProfile);
                        view.addFriend(user);
                        model.getLocal()
                                .insertUsers(user);
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
    public void addFriend(int idUser) {
        model.getSocket()
                .postFriend(model.getIdProfile(), idUser)
                .subscribe(new Observer<POSTFriend>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(POSTFriend friend) {
                        model.getLocal()
                                .insertFriends(GsonToTable.tableFriends(friend));
                        view.dismissProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissProgressBar();
                        view.showErrorAddFriend();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setNotif(int idUser, boolean notif) {
        model.getLocal()
                .updateNotif(notif, idUser);
    }

    @Override
    public void getUser(int idUser) {
        model.getLocal()
                .getUser(idUser)
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {
                        view.setDialogUser(tableUsers.getAvatar(),
                                tableUsers.getNick(),
                                "ID " + tableUsers.getIdusers(),
                                tableUsers.getBio(),
                                tableUsers.isNotif());
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
    public void deleteFriend(int idUser) {
        model.getLocal()
                .getFriend(idUser)
                .subscribe(new MaybeObserver<TableFriends>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableFriends tableFriends) {
                        deleteSocketFriend(tableFriends.getIdfriends());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void deleteSocketFriend(int idFriends) {
        model.getSocket()
                .deleteFriend(idFriends)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal()
                                .deleteFriend(idFriends);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public void startDialog(int idUser_2) {
        view.startDialog(model.getIdProfile(), idUser_2);
    }

    @Override
    public void onDestroy() {
        composite.clear();
        view = null;
        model = null;
    }
}
