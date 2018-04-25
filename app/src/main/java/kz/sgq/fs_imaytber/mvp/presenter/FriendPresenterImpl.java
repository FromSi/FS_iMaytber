package kz.sgq.fs_imaytber.mvp.presenter;

import android.util.Log;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.ResourceMaybeObserver;
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
        init();
    }

    void init() {
        composite.add(
                model.getLocal()
                        .getFriends()
                        .subscribeWith(new DisposableSubscriber<List<TableFriends>>() {
                            @Override
                            public void onNext(List<TableFriends> friendsList) {
                                List<Integer> list = view.getIdUsers();
                                for (int i = 0; i < friendsList.size(); i++) {
                                    if (friendsList.get(i).getIduser_1() != model.getIdProfile()) {
                                        boolean bool = true;
                                        for (int j = 0; j < list.size(); j++) {
                                            if (friendsList.get(i).getIduser_1() ==
                                                    list.get(j))
                                                bool = false;
                                        }
                                        if (bool)
                                            handlerUser(friendsList.get(i).getIduser_1());
                                    } else {
                                        boolean bool = true;
                                        for (int j = 0; j < list.size(); j++) {
                                            if (friendsList.get(i).getIduser_2() ==
                                                    list.get(j))
                                                bool = false;
                                        }
                                        if (bool)
                                            handlerUser(friendsList.get(i).getIduser_2());
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
    public void onDestroy() {
        Log.d("ExitAndDestroy", this.getClass().getName());
        composite.clear();
        view = null;
        model = null;
    }
}
