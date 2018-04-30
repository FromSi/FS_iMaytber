package kz.sgq.fs_imaytber.mvp.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.ResourceMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.mvp.model.HistoryModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.HistoryModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.HistoryPresenter;
import kz.sgq.fs_imaytber.mvp.view.HistoryView;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableProfile;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.HistoryZIP;

public class HistoryPresenterImpl implements HistoryPresenter {
    private HistoryView view;
    private HistoryModel model;
    private CompositeDisposable composite;
    private int size = 0;

    public HistoryPresenterImpl(HistoryView view) {
        this.view = view;
        Log.d("DestroyPause","HistoryPresenterImpl");
        model = new HistoryModelImpl();
        composite = new CompositeDisposable();
        init();
    }

    private void init() {
        composite = new CompositeDisposable();
        composite.add(model.getLocal()
                .getProfile()
                .subscribeWith(new DisposableSubscriber<TableProfile>() {
                    @Override
                    public void onNext(TableProfile profile) {
                        model.setIdUser(profile.getIduser());
                        composite.clear();
//                        handlerChats();
                        handlerMessage();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    private void handlerMessage() {
        composite.add(model.getLocal()
                .getMessage()
                .subscribeWith(new DisposableSubscriber<TableMessages>() {
                    @Override
                    public void onNext(TableMessages messages) {
                        Log.d("TestTagMy2", "onNext");
                        handlerChats();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("TestTagMy2", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TestTagMy2", "onComplete");
                    }
                }));
    }

    private void handlerChats() {
        model.getLocal()
                .getChats()
                .subscribe(new MaybeObserver<List<TableChats>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<TableChats> chatsList) {
                        if (chatsList.size() != model.getIdChatList().size()){
                            Log.d("TestTagMy2", "onSuccess handlerChats");
                            model.clearListIdUser_2();
                            model.clearListIdChat();
                            for (int i = 0; i < chatsList.size(); i++) {
                                if (model.getIdUser() != chatsList.get(i).getIduser_1())
                                    model.addListIdUser_2(chatsList.get(i).getIduser_1());
                                else
                                    model.addListIdUser_2(chatsList.get(i).getIduser_2());

                                model.addIdChatList(chatsList.get(i).getIdchats());
                            }
                        }
                        Log.d("TestTagMy2", "zipHandler");
                        zipHandler();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void zipHandler() {
        view.clearHistory();
        for (int i = 0; i < model.getIdChatList().size(); i++) {
            Maybe.zip(model.getLocal().getDialog(model.getIdChatList().get(i)),
                    model.getLocal().getUser(model.getListIdUser_2().get(i)),
                    (messages, tableUsers) -> new HistoryZIP(tableUsers.getAvatar(),
                            tableUsers.getNick(),
                            tableUsers.getIdusers(),
                            messages.getContent(),
                            messages.getTime()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MaybeObserver<HistoryZIP>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(HistoryZIP historyZIP) {
                            Log.d("TestTagMy", "Maybe" + 1);
                            view.addHistory(historyZIP);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        composite.clear();
        view = null;
        model = null;
    }

    @Override
    public void startDialog(int idUser_2) {
        view.startDialog(model.getIdUser(), idUser_2);
    }
}
