package kz.sgq.fs_imaytber.mvp.view;

import java.util.List;

import kz.sgq.fs_imaytber.room.table.TableMessages;

public interface DialogView {
    void setIdUserAdapter(int id);
    void addMessage(TableMessages messages);
    String getText();
    void hideHandler();
    void errorMessage();
    void setToolBar(String nick);
    void setAvatar(String avatar);
}
