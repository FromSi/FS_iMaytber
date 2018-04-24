package kz.sgq.fs_imaytber.mvp.presenter;

import io.reactivex.observers.DisposableObserver;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTUser;
import kz.sgq.fs_imaytber.mvp.model.LoginModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.LoginModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.LoginPresenter;
import kz.sgq.fs_imaytber.mvp.view.SignupView;
import kz.sgq.fs_imaytber.util.GsonToTable;

public class SignupPresenterImpl implements LoginPresenter {
    private SignupView view;
    private LoginModel model;

    public SignupPresenterImpl(SignupView view) {
        this.view = view;
        model = new LoginModelImpl();
    }

    @Override
    public void handlerClick() {
        model.getLocal().deleteAll();
        boolean nick = view.getNick().length() >= model.getMinSize();
        boolean login = view.getLogin().length() >= model.getMinSize();
        boolean password = view.getPassword().length() >= model.getMinSize();

        if (nick && login && password) {
            model.getSocket()
                    .postUser(view.getUrlAvatar(), view.getNick(),
                            view.getLogin(), view.getPassword(),
                            view.getToken())
                    .subscribe(new DisposableObserver<POSTUser>() {
                        @Override
                        public void onNext(POSTUser postUser) {
                            model.getLocal()
                                    .insertProfile(GsonToTable.tableProfile(postUser));
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
        } else {
            if (!nick)
                view.showErrorNick();
            if (!login)
                view.showErrorLogin();
            if (!password)
                view.showErrorPassword();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        model = null;
    }
}
