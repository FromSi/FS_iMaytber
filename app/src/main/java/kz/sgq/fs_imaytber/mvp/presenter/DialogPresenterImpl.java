package kz.sgq.fs_imaytber.mvp.presenter;

import android.util.Log;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTMessage;
import kz.sgq.fs_imaytber.mvp.model.DialogModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.DialogModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.DialogPresenter;
import kz.sgq.fs_imaytber.mvp.view.DialogView;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.FS_RC4;
import kz.sgq.fs_imaytber.util.GsonToTable;

public class DialogPresenterImpl implements DialogPresenter {
    private DialogView view;
    private DialogModel model;
    private CompositeDisposable composite;
    private CompositeDisposable compositeListener;

    public DialogPresenterImpl(DialogView view, int idUser_1, int idUser_2) {
        this.view = view;
        model = new DialogModelImpl(idUser_1, idUser_2);
        composite = new CompositeDisposable();
        this.view.setIdUserAdapter(model.getIdUser_1());
        initIdChat();
        initAvatarAndNick();
    }

    private void initIdChat() {
        model.getLocal().getChatKey(model.getIdUser_2())
                .subscribe(new MaybeObserver<TableChats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableChats chats) {
                        model.setIdChat(chats.getIdchats());
                        init();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        listenerAddIdChat();
                    }
                });
    }

    private void listenerAddIdChat(){
        compositeListener = new CompositeDisposable();
        compositeListener.add(model.getLocal()
        .getIdChatPref(model.getIdUser_2())
        .subscribeWith(new DisposableSubscriber<TableChats>() {
            @Override
            public void onNext(TableChats chats) {
                model.setKey(chats.getKey());
                model.setIdChat(chats.getIdchats());
                init();
                compositeListener.clear();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    private void init() {
        if (model.getIdChat() != 0)
            composite.add(
                    model.getLocal()
                            .getMessages(model.getIdChat())
                            .subscribeWith(new DisposableSubscriber<List<TableMessages>>() {
                                @Override
                                public void onNext(List<TableMessages> messagesList) {
                                    if (model.getIdMessage() != null &&
                                            model.getIdMessage().size() != 0)
                                        for (int i = 0; i < messagesList.size(); i++) {
                                            boolean check = true;
                                            for (int j = 0; j < model.getIdMessage().size(); j++) {
                                                if (messagesList.get(i).getIdmessages() ==
                                                        model.getIdMessage().get(j))
                                                    check = false;
                                            }
                                            if (check) {
                                                model.addIdMessage(messagesList.get(i).getIdmessages());
                                                view.addMessage(messagesList.get(i));
                                            }
                                        }
                                    else {
                                        for (int i = 0; i < messagesList.size(); i++) {
                                            model.addIdMessage(messagesList.get(i).getIdmessages());
                                            view.addMessage(messagesList.get(i));
                                        }
                                    }
                                }

                                @Override
                                public void onError(Throwable t) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            })
            );
    }

    private void initAvatarAndNick() {
        model.getLocal().getUser(model.getIdUser_2())
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {
                        model.setNick(tableUsers.getNick());
                        model.setAvatar(tableUsers.getAvatar());
                        initToolBar();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initToolBar() {
        view.setToolBar(model.getNick());
        view.setAvatar(model.getAvatar());
    }

    private String cryText() {
        if (model.getKey() != null) {
            return new FS_RC4(model.getKey(), view.getText())
                    .start();
        } else {
            return view.getText();
        }
    }

    @Override
    public void handlerMessage() {
        model.getSocket()
                .postMessage(model.getIdUser_1(),
                        model.getIdUser_2(),
                        cryText())
                .subscribe(new Observer<POSTMessage>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("TagListenerIdChat","onSubscribe");

                    }

                    @Override
                    public void onNext(POSTMessage message) {
                        if (message.getKey() != null) {
                            model.setKey(message.getKey());
                            model.setIdChat(Integer.parseInt(message.getIdchat()));
                            compositeListener.clear();
                            init();
                            model.getLocal()
                                    .insertChats(new TableChats(Integer
                                            .parseInt(message.getIdchat()),
                                            model.getIdUser_1(),
                                            model.getIdUser_2(),
                                            message.getKey()));
                        }

                        if (model.getKey() != null) {
                            handlerAddMessage(Integer.parseInt(message.getIdmessage()),
                                    GsonToTable.tableMessages(message, model.getKey()));
                        } else {
                            handlerAddMessage(Integer.parseInt(message.getIdmessage()),
                                    GsonToTable.tableMessages(message, message.getKey()));
                        }

                        view.hideHandler();
                        Log.d("TagListenerIdChat","onNext");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TagListenerIdChat","onError");
                        view.errorMessage();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TagListenerIdChat","onComplete");

                    }
                });
    }

    private void handlerAddMessage(int id, TableMessages messages) {
        model.addIdMessage(id);
        model.getLocal()
                .insertMessage(messages);
        view.addMessage(messages);
    }

    @Override
    public void onDestroy() {
        composite.clear();
        view = null;
        model = null;
    }
}
