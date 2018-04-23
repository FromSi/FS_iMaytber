package kz.sgq.fs_imaytber.mvp.view;

public interface SignupView {
    String getNick();
    String getLogin();
    String getPassword();
    String getToken();
    void setupProfile();
    void showErrorNick();
    void showErrorLogin();
    void showErrorPassword();
    void showErrorConnect();
    void showProgressBar();
    void dismissProgressBar();
    void startActivity();
}
