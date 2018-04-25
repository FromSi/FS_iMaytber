package kz.sgq.fs_imaytber.mvp.presenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTMessage;
import kz.sgq.fs_imaytber.mvp.model.DialogModelImpl;
import kz.sgq.fs_imaytber.mvp.model.interfaces.DialogModel;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.DialogPresenter;
import kz.sgq.fs_imaytber.mvp.view.DialogView;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.util.FS_RC4;
import kz.sgq.fs_imaytber.util.GsonToTable;

public class DialogPresenterImpl implements DialogPresenter {
    private DialogView view;
    private DialogModel model;
    private CompositeDisposable composite;

    public DialogPresenterImpl(DialogView view, int idUser_1,int idUser_2) {
        this.view = view;
        model = new DialogModelImpl(idUser_1, idUser_2);
        composite = new CompositeDisposable();
        init();
        initToolBar();
    }

    private void init() {
        if (model.getIdChat() != 0)
        composite.add(
                model.getLocal()
                        .getMessages(model.getIdChat())
                        .subscribeWith(new DisposableSubscriber<List<TableMessages>>() {
                            @Override
                            public void onNext(List<TableMessages> messagesList) {
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

    private void initToolBar(){
        view.setToolBar(model.getNick());
        view.setAvatar(model.getAvatar());
    }

    private String cryText(){
        if (!model.getKey().isEmpty()){
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

                    }

                    @Override
                    public void onNext(POSTMessage message) {
                        if (!message.getKey().isEmpty())
                            model.setKey(message.getKey());
                        model.getLocal()
                                .insertMessage(GsonToTable.tableMessages(message));
                        view.addMessage(GsonToTable.tableMessages(message));
                        view.hideHandler();

                        if (model.getIdChat() == 0)
                            init();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.errorMessage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        composite.clear();
        view = null;
        model = null;
    }
}
