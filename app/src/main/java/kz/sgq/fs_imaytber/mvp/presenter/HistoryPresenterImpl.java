package kz.sgq.fs_imaytber.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    public HistoryPresenterImpl(HistoryView view) {
        this.view = view;
        model = new HistoryModelImpl();
        composite = new CompositeDisposable();
        checkItem();
    }

    private void checkItem() {
        model.getLocal()
                .getChat()
                .subscribe(new MaybeObserver<TableChats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableChats chats) {
                        init();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        init();
                        view.showNullItem();
                    }
                });
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
                        handlerChats();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

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
                        if (chatsList.size() != model.getIdChatList().size()) {
                            model.clearListIdUser_2();
                            model.clearListIdChat();
                            model.clearReadList();
                            for (int i = 0; i < chatsList.size(); i++) {
                                if (model.getIdUser() != chatsList.get(i).getIduser_1())
                                    model.addListIdUser_2(chatsList.get(i).getIduser_1());
                                else
                                    model.addListIdUser_2(chatsList.get(i).getIduser_2());

                                model.addReadList(chatsList.get(i).getRead());
                                model.addIdChatList(chatsList.get(i).getIdchats());
                            }
                        } else {
                            model.clearReadList();
                            for (int i = 0; i < chatsList.size(); i++) {
                                model.addReadList(chatsList.get(i).getRead());
                            }
                        }
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
        List<HistoryZIP> historyList = new ArrayList<>();
        view.clearHistory();
        for (int i = 0; i < model.getIdChatList().size(); i++) {
            final int index = i;
            Maybe.zip(model.getLocal().getDialog(model.getIdChatList().get(i)),
                    model.getLocal().getUser(model.getListIdUser_2().get(i)),
                    (messages, tableUsers) -> new HistoryZIP(tableUsers.getAvatar(),
                            tableUsers.getNick(),
                            tableUsers.getIdusers(),
                            messages.getIdmessages(),
                            messages.getContent(),
                            messages.getTime(),
                            model.getReadList().get(index)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MaybeObserver<HistoryZIP>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(HistoryZIP historyZIP) {
                            historyList.add(historyZIP);
                            if (model.getIdChatList().size() > 1) {
                                if (historyList.size() == model.getIdChatList().size())
                                    sortingMessage(historyList);
                            } else if (model.getIdChatList().size() == 1) {
                                view.addHistory(historyZIP);
                            }

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
    public void getUser(int idUser) {
        model.getLocal()
                .getUser(idUser)
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {
                        view.setDialogUser(tableUsers.getAvatar(),
                                tableUsers.getNick(),
                                "ID " + tableUsers.getIdusers(),
                                tableUsers.getBio(),
                                false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void sortingMessage(List<HistoryZIP> list) {
        int[] n = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            n[i] = list.get(i).getIdMessage();
        }

        for (int i = 0; i < n.length - 1; i++) {
            for (int j = 0; j < n.length - 1; j++) {
                if (n[j] > n[j + 1]) {
                    int a = n[j];
                    n[j] = n[j + 1];
                    n[j + 1] = a;
                }
            }
        }

        for (int i = 0; i < n.length; i++) {
            List<HistoryZIP> fan = list;
            for (int j = 0; j < fan.size(); j++) {
                if (n[i] == fan.get(j).getIdMessage()) {
                    view.addHistory(list.get(j));
                    fan.remove(j);
                    break;
                }
            }
        }
        view.setPosition();
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
