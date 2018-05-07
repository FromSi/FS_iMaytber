package kz.sgq.fs_imaytber.mvp.view;

import java.util.List;

import kz.sgq.fs_imaytber.room.table.TableUsers;

public interface FriendView {
    void addFriend(TableUsers user);

    void deleteFriend(int idUser);

    void showErrorAddFriend();

    List<Integer> getIdUsers();

    void dismissProgressBar();

    void startDialog(int idUser_1, int idUser_2);

    void showNullItem();

    void setDialogUser(String avatar, String nick, String login, String bio, boolean switchBool);
}
