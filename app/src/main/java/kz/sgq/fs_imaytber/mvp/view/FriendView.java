package kz.sgq.fs_imaytber.mvp.view;

import java.util.List;

import kz.sgq.fs_imaytber.room.table.TableUsers;

public interface FriendView {
    void addFriend(TableUsers user);
    void showErrorAddFriend();
    List<Integer> getIdUsers();
    void dismissProgressBar();
    void startDialog(int idUser_1, int idUser_2);
}
