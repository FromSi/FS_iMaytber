package kz.sgq.fs_imaytber.mvp.presenter;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.mvp.model.MainModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.MainModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.MainPresenter;
import kz.sgq.fs_imaytber.mvp.view.MainView;
import kz.sgq.fs_imaytber.room.table.TableProfile;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private MainModel model;
    private CompositeDisposable composite;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        composite = new CompositeDisposable();
        model = new MainModelImpl();
        handlerProfile();
    }

    @Override
    public void exitActivity() {
        model.getSocket()
                .putToken(model.getIdUser(),
                        "no token")
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal().deleteAll();
                        view.exitActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        model.getLocal().deleteAll();
                        view.exitActivity();
                        view.showErrorExit();
                    }
                });
    }

    @Override
    public void onDestroy() {
        composite.clear();
        view = null;
        model = null;
    }

    private void handlerProfile() {
        composite.add(
                model.getLocal()
                        .getProfile()
                        .subscribeWith(new DisposableSubscriber<TableProfile>() {
                            @Override
                            public void onNext(TableProfile profile) {
                                model.setIdUser(profile.getIduser());
                                model.initUsers(profile.getIduser());
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
                        }));
    }
}
