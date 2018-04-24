package kz.sgq.fs_imaytber.mvp.view;

public interface SignupView {
    String getNick();
    String getLogin();
    String getPassword();
    String getToken();
    String getUrlAvatar();
    void setupProfile();
    void showErrorNick();
    void showErrorLogin();
    void showErrorPassword();
    void showErrorConnect();
    void dismissProgressBar();
    void startActivity();
}
