package kz.sgq.fs_imaytber.mvp.presenter;

import java.util.Date;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTMessage;
import kz.sgq.fs_imaytber.mvp.model.DialogModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.DialogModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.DialogPresenter;
import kz.sgq.fs_imaytber.mvp.view.DialogView;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.FS_RC4;
import kz.sgq.fs_imaytber.util.GsonToTable;
import kz.sgq.fs_imaytber.util.Message;
import kz.sgq.fs_imaytber.util.MessageCondition;

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

    private void listenerAddIdChat() {
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
                                                model.addIdMessage(messagesList.get(i)
                                                        .getIdmessages());
                                                view.uploadCondition(view
                                                                .addMessage(new Message(messagesList
                                                                        .get(i).getIduser(),
                                                                        messagesList.get(i)
                                                                                .getContent(),
                                                                        messagesList.get(i)
                                                                                .getTime())),
                                                        MessageCondition.DONE);
                                            }
                                        }
                                    else {
                                        for (int i = 0; i < messagesList.size(); i++) {
                                            model.addIdMessage(messagesList.get(i)
                                                    .getIdmessages());
                                            view.uploadCondition(view
                                                            .addMessage(new Message(messagesList
                                                                    .get(i).getIduser(),
                                                                    messagesList.get(i)
                                                                            .getContent(),
                                                                    messagesList.get(i)
                                                                            .getTime())),
                                                    MessageCondition.DONE);
                                        }
                                    }
                                    localReadCheck();
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


    @Override
    public void getUser() {
        model.getLocal()
                .getUser(model.getIdUser_2())
                .subscribe(new MaybeObserver<TableUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableUsers tableUsers) {
                        view.showDialogUser(model.getIdUser_2());
                        view.setDialogUser(tableUsers.getAvatar(),
                                tableUsers.getNick(),
                                "ID " + tableUsers.getIdusers(),
                                tableUsers.getBio(),
                                tableUsers.isNotif());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    private String cryText(String content) {
        if (model.getKey() != null) {
            return new FS_RC4(model.getKey(), content)
                    .start();
        } else {
            return content;
        }
    }

    private void handlerRead() {
        model.getSocket()
                .putRead(model.getIdChat(), 0)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null) {
                            model.getLocal()
                                    .updateChatRead(0, model.getIdChat());
                            model.setRead(0);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void localReadCheck() {
        model.getLocal()
                .getIdChat(model.getIdChat())
                .subscribe(new MaybeObserver<TableChats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableChats chats) {
                        if (chats.getRead() != 0) {
                            model.setRead(chats.getRead());
                            handlerRead();
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

    @Override
    public void handlerReadMessage() {
        String content = view.getText();
        String time = new Date().toString();
        int idMessage = view.addMessage(new Message(model.getIdUser_2(),
                content, time));
        if (model.getRead() != 0)
            model.getSocket()
                    .putRead(model.getIdChat(), 0)
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            model.getLocal()
                                    .updateChatRead(0, model.getIdChat());
                            model.setRead(0);
                            handlerMessage(content, time, idMessage);
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.uploadCondition(idMessage, MessageCondition.ERROR);
                        }
                    });
        else
            handlerMessage(content, time, idMessage);
    }

    @Override
    public void deleteFriend() {
        model.getLocal()
                .getFriend(model.getIdUser_2())
                .subscribe(new MaybeObserver<TableFriends>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TableFriends tableFriends) {
                        deleteSocketFriend(tableFriends.getIdfriends());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addFriend() {
        model.getSocket()
                .postFriend(model.getIdUser_1(),
                        model.getIdUser_2())
                .subscribe(new Observer<POSTFriend>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(POSTFriend friend) {
                        model.getLocal()
                                .insertFriends(GsonToTable.tableFriends(friend));
                        view.showAddFriend();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void deleteSocketFriend(int idFriends) {
        model.getSocket()
                .deleteFriend(idFriends)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        model.getLocal()
                                .deleteFriend(idFriends);
                        view.showDeleteFriend();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void handlerMessage(String content, String time, int idMessage) {
        model.getSocket()
                .postMessage(model.getIdUser_1(),
                        model.getIdUser_2(),
                        cryText(content),
                        time)
                .subscribe(new Observer<POSTMessage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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
                                            message.getKey(),
                                            0));
                        }

                        view.uploadCondition(idMessage, MessageCondition.DONE);
                        model.addIdMessage(Integer.parseInt(message.getIdmessage()));
                        if (model.getKey() != null) {
                            model.getLocal()
                                    .insertMessage(GsonToTable
                                            .tableMessages(message, model.getKey()));
                        } else {
                            model.getLocal()
                                    .insertMessage(GsonToTable
                                            .tableMessages(message, message.getKey()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.uploadCondition(idMessage, MessageCondition.ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setNotif(int idUser, boolean notif) {
        model.getLocal()
                .updateNotif(notif, idUser);
    }

    @Override
    public void onDestroy() {
        composite.clear();
        view = null;
        model = null;
    }
}
