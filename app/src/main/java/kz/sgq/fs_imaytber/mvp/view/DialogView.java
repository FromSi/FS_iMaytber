package kz.sgq.fs_imaytber.mvp.view;

import kz.sgq.fs_imaytber.util.Message;
import kz.sgq.fs_imaytber.util.MessageCondition;

public interface DialogView {
    void setIdUserAdapter(int id);

    int addMessage(Message messages);

    String getText();

    void showDeleteFriend();

    void showAddFriend();

    void showDialogUser(int idUser);

    void setToolBar(String nick);

    void setAvatar(String avatar);

    void uploadCondition(int idMessage, MessageCondition condition);

    void setDialogUser(String avatar, String nick, String login, String bio, boolean switchBool);
}
