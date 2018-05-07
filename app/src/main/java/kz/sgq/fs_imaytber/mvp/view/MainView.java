package kz.sgq.fs_imaytber.mvp.view;

public interface MainView {
    void setNick(String nick);

    void setLogin(String login);

    void setAvatar(String url);

    void exitActivity();

    void showErrorExit();
}
