package kz.sgq.fs_imaytber.mvp.view;

public interface SettingsView {
    void setProfile(String nick, String login, String id);

    void setNick(String nick);

    void setPassword();

    void setAvatar(String uri);

    void setBio(String bio);

    String getUrlAvatar();

    void showSuccess();

    void showError();
}
