package kz.sgq.fs_imaytber.mvp.presenter;

import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.mvp.model.MainModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.MainModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.MainPresenter;
import kz.sgq.fs_imaytber.mvp.view.MainView;
import kz.sgq.fs_imaytber.room.table.TableProfile;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private MainModel model;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        model = new MainModelImpl();
        handlerProfile();
    }

    @Override
    public void exitActivity() {
        model.getLocal().deleteAll();
        view.exitActivity();
    }

    @Override
    public void onDestroy() {
        view = null;
        model = null;
    }

    private void handlerProfile() {
        model.getLocal()
                .getProfile()
                .subscribe(new DisposableSubscriber<TableProfile>() {
                    @Override
                    public void onNext(TableProfile profile) {
                        view.setNick(profile.getNick());
                        view.setLogin(profile.getLogin() + "#" + profile.getIduser());
                        view.setAvatar(profile.getAvatar());
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
