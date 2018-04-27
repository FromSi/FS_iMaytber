package kz.sgq.fs_imaytber.mvp.view;

import java.util.List;

import kz.sgq.fs_imaytber.util.HistoryZIP;

public interface HistoryView {
    void addHistory(HistoryZIP message);

    void clearHistory();

    void startDialog(int idUser_1, int idUser_2);
}
