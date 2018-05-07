package kz.sgq.fs_imaytber.mvp.view;

import kz.sgq.fs_imaytber.util.HistoryZIP;

public interface HistoryView {
    void addHistory(HistoryZIP message);

    void clearHistory();

    void setPosition();

    void setDialogUser(String avatar, String nick, String login, String bio, boolean switchBool);

    void startDialog(int idUser_1, int idUser_2);

    void showNullItem();
}
