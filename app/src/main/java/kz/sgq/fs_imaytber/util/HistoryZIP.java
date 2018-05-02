package kz.sgq.fs_imaytber.util;

public class HistoryZIP {
    private String avatar;
    private String nick;
    private int idUser;
    private int idMessage;
    private String content;
    private String time;

    public HistoryZIP(String avatar, String nick, int idUser, int idMessage, String content, String time) {
        this.avatar = avatar;
        this.nick = nick;
        this.idUser = idUser;
        this.idMessage = idMessage;
        this.content = content;
        this.time = time;
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

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
