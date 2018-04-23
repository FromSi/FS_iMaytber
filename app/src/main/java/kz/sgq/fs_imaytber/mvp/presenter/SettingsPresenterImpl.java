package kz.sgq.fs_imaytber.mvp.presenter;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.mvp.model.SettingsModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.SettingsModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.SettingsPresenter;
import kz.sgq.fs_imaytber.mvp.view.SettingsView;
import kz.sgq.fs_imaytber.room.table.TableProfile;

public class SettingsPresenterImpl implements SettingsPresenter {
    private SettingsView view;
    private SettingsModel model;

    public SettingsPresenterImpl(SettingsView view) {
        this.view = view;
        model = new SettingsModelImpl();
        init();
    }

    private void init() {
        model.getLocal()
                .getProfile()
                .subscribe(new DisposableSubscriber<TableProfile>() {
                    @Override
                    public void onNext(TableProfile profile) {
                        view.setProfile(profile.getNick(),
                                "Логин: " + profile.getLogin(),
                                "ID " + profile.getIduser());
                        model.setIdUser(profile.getIduser());
                        model.setLoginUser(profile.getLogin());
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
    public void onDestroy() {
        view = null;
        model = null;
    }

    @Override
    public void onClickNick(String nick) {
        model.getSocket()
                .putNick(model.getIdUser(), nick)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal().updateNick(nick);
                        view.setNick(nick);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onClickPassword(String password) {
        model.getSocket()
                .putPassword(model.getLoginUser(), password)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal().updatePassword(password);
                        view.setPassword();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
