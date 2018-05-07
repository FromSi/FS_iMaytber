package kz.sgq.fs_imaytber.mvp.presenter;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
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

    private CompositeDisposable composite;

    public SettingsPresenterImpl(SettingsView view) {
        this.view = view;
        composite = new CompositeDisposable();
        model = new SettingsModelImpl();
        init();
    }

    private void init() {
        composite.add(
                model.getLocal()
                        .getProfile()
                        .subscribeWith(new DisposableSubscriber<TableProfile>() {
                            @Override
                            public void onNext(TableProfile profile) {
                                if (profile.getBio() != null)
                                    view.setBio(profile.getBio());
                                view.setAvatar(profile.getAvatar());
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
                        }));
    }

    @Override
    public void onDestroy() {
        composite.clear();
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
                        view.showSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }
                });
    }

    @Override
    public void onClickBio(String bio) {
        model.getSocket()
                .putBio(model.getIdUser(), bio)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal().updateBio(bio);
                        view.setBio(bio);
                        view.showSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
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
                        view.showSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }
                });
    }

    @Override
    public void handlerAvatar() {
        model.getSocket()
                .putAvatar(model.getIdUser(),
                        view.getUrlAvatar())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal()
                                .updateAvatar(view.getUrlAvatar());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }
                });
    }

    @Override
    public void handlerAvatar(String url) {
        model.getSocket()
                .putAvatar(model.getIdUser(),
                        view.getUrlAvatar())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal()
                                .updateAvatar(url);
                        view.showSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }
                });
    }
}
