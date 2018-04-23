package kz.sgq.fs_imaytber.mvp.view;

public interface LoginView {
    String getLogin();
    String getPassword();
    void setupProfile();
    void showErrorLogin();
    void showErrorPassword();
    void showErrorConnect();
    void showProgressBar();
    void dismissProgressBar();
    void startActivity();
}
