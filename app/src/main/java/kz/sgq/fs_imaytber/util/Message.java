package kz.sgq.fs_imaytber.util;

public class Message {
    private int idUser;
    private int idMessage;
    private String content;
    private String time;

    public Message(int idUser,int idMessage, String content, String time) {
        this.idUser = idUser;
        this.idMessage = idMessage;
        this.content = content;
        this.time = time;
    }
    public Message(int idUser, String content, String time) {
        this.idUser = idUser;
        this.content = content;
        this.time = time;
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
