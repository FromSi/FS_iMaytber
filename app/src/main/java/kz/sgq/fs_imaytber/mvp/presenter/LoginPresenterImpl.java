package kz.sgq.fs_imaytber.mvp.presenter;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETLogin;
import kz.sgq.fs_imaytber.mvp.model.LoginModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.LoginModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.LoginPresenter;
import kz.sgq.fs_imaytber.mvp.view.LoginView;
import kz.sgq.fs_imaytber.util.GsonToTable;
import kz.sgq.fs_imaytber.util.LoginZIP;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView view;
    private LoginModel model;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        model = new LoginModelImpl();
    }

    @Override
    public void handlerClick() {
        model.getLocal().deleteAll();
        boolean login = view.getLogin().length() >= model.getMinSize();
        boolean password = view.getPassword().length() >= model.getMinSize();

        if (login && password) {
            view.showProgressBar();
            model.getSocket()
                    .getLogin(view.getLogin(), view.getPassword())
                    .subscribe(new DisposableObserver<GETLogin>() {
                        @Override
                        public void onNext(GETLogin login) {
                            model.getLocal()
                                    .insertProfile(GsonToTable.tableProfile(login));
                            handlerREST(Integer.parseInt(login.getIduser()));
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.dismissProgressBar();
                            view.showErrorConnect();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            if (!login)
                view.showErrorLogin();
            if (!password)
                view.showErrorPassword();
        }
    }

    private void handlerREST(int iduser) {
        Observable.zip(model.getSocket().getChats(iduser),
                model.getSocket().getFriend(iduser),
                model.getSocket().getMessage(iduser),
                (chatsList, friendList, messageList) -> new LoginZIP(GsonToTable.tableChats(chatsList),
                        GsonToTable.tableMessages(messageList),
                        GsonToTable.tableFriends(friendList)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginZIP>() {
                    @Override
                    public void onNext(LoginZIP loginZIP) {
                        model.getLocal().insertChats(loginZIP.getChats());
                        model.getLocal().insertMessage(loginZIP.getMessages());
                        model.getLocal().insertFriends(loginZIP.getFriends());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissProgressBar();
                        view.showErrorConnect();
                    }

                    @Override
                    public void onComplete() {
                        view.dismissProgressBar();
                        view.startActivity();
                    }
                });
    }

    @Override
    public void onDestroy() {
        Log.d("ExitAndDestroy", this.getClass().getName());
        view = null;
        model = null;
    }

}
