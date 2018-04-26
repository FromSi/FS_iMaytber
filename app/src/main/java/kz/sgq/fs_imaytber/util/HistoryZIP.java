package kz.sgq.fs_imaytber.util;

public class HistoryZIP {
    private String avatar;
    private String nick;
    private int idUser;
    private String content;

    public HistoryZIP(String avatar, String nick, int idUser, String content) {
        this.avatar = avatar;
        this.nick = nick;
        this.idUser = idUser;
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
