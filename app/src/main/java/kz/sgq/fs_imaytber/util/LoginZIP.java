package kz.sgq.fs_imaytber.util;

import java.util.List;

import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableMessages;

public class LoginZIP {
    private List<TableChats> chats;
    private List<TableMessages> messages;
    private List<TableFriends> friends;

    public LoginZIP(List<TableChats> chats, List<TableMessages> messages, List<TableFriends> friends) {
        this.chats = chats;
        this.messages = messages;
        this.friends = friends;
    }

    public List<TableChats> getChats() {
        return chats;
    }

    public List<TableMessages> getMessages() {
        return messages;
    }

    public List<TableFriends> getFriends() {
        return friends;
    }
}
