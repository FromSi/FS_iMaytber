package kz.sgq.fs_imaytber.mvp.presenter.interfaces;

public interface HistoryPresenter {
    void onDestroy();

    void getUser(int idUser);

    void startDialog(int idUser_2);
}
