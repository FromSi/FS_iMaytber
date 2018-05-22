package kz.sgq.fs_imaytber.mvp.presenter.interfaces;

public interface DialogPresenter {
    void handlerReadMessage();

    void deleteFriend();

    void addFriend();

    void setNotif(int idUser, boolean notif);

    void onDestroy();

    void getUser();
    void deleteMessage(int idMessage);
}
