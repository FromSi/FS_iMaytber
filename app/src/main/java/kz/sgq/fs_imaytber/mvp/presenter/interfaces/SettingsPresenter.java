package kz.sgq.fs_imaytber.mvp.presenter.interfaces;

public interface SettingsPresenter {
    void onDestroy();
    void onClickNick(String nick);
    void onClickPassword(String password);
    void handlerAvatar();
    void handlerAvatar(String url);
}
