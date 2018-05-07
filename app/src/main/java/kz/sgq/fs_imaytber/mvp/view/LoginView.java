package kz.sgq.fs_imaytber.mvp.view;

public interface LoginView {
    String getLogin();

    String getPassword();

    String getToken();

    void showErrorLogin();

    void showErrorPassword();

    void showErrorConnect();

    void showProgressBar();

    void dismissProgressBar();

    void startActivity();
}
