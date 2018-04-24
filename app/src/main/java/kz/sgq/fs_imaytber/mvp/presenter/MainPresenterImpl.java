package kz.sgq.fs_imaytber.mvp.presenter;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
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
        model.getLocal().deleteAll();
        view.exitActivity();
    }

    @Override
    public void onDestroy() {
        Log.d("ExitAndDestroy", this.getClass().getName());
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
                                Log.d("testNull", profile.getNick());
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
