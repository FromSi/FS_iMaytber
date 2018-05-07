package kz.sgq.fs_imaytber.mvp.presenter.interfaces;

public interface FriendPresenter {
    void deleteFriend(int idUser);

    void addFriend(int idUser);

    void setNotif(int idUser, boolean notif);

    void getUser(int idUser);

    void startDialog(int idUser_2);

    void onDestroy();
}
