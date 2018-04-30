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
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.util.FS_RC4;
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
                            handlerToken(Integer.parseInt(login.getIduser()));
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

    private void handlerToken(int idUser) {
        model.getSocket()
                .putToken(idUser, view.getToken())
                .subscribe();
    }

    private void handlerREST(int idUser) {
        Observable.zip(model.getSocket().getChats(idUser),
                model.getSocket().getFriend(idUser),
                model.getSocket().getMessage(idUser),
                (chatsList, friendList, messageList) -> new LoginZIP(GsonToTable.tableChats(chatsList),
                        GsonToTable.tableMessages(messageList),
                        GsonToTable.tableFriends(friendList)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginZIP>() {
                    @Override
                    public void onNext(LoginZIP loginZIP) {
                        model.getLocal().insertChats(loginZIP.getChats());
                        model.getLocal().insertFriends(loginZIP.getFriends());

                        for (int i = 0; i < loginZIP.getMessages().size(); i++) {
                            for (int j = 0; j < loginZIP.getChats().size(); j++) {
                                if (loginZIP.getChats().get(j).getIdchats() ==
                                        loginZIP.getMessages().get(i).getIdchats())
                                    model.getLocal()
                                            .insertMessage(new TableMessages(loginZIP.getMessages().get(i).getIdmessages(),
                                                    loginZIP.getMessages().get(i).getIdchats(),
                                                    loginZIP.getMessages().get(i).getIduser(),
                                                    new FS_RC4(loginZIP.getChats().get(j).getKey(),
                                                            loginZIP.getMessages().get(i).getContent())
                                                            .start(),
                                                    loginZIP.getMessages().get(i).getTime()));
                            }
                        }

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
        view = null;
        model = null;
    }

}
