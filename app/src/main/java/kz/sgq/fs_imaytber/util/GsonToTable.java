package kz.sgq.fs_imaytber.util;

import java.util.ArrayList;
import java.util.List;

import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETChats;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETLogin;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETMessage;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTMessage;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTUser;
import kz.sgq.fs_imaytber.room.table.TableChats;
import kz.sgq.fs_imaytber.room.table.TableFriends;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableProfile;
import kz.sgq.fs_imaytber.room.table.TableUsers;

public class GsonToTable {
    public static List<TableChats> tableChats(List<GETChats> chatsList) {
        List<TableChats> list = new ArrayList<>();
        for (int i = 0; i < chatsList.size(); i++) {
            list.add(new TableChats(Integer.parseInt(chatsList.get(i).getIdchat()),
                    Integer.parseInt(chatsList.get(i).getIduser1()),
                    Integer.parseInt(chatsList.get(i).getIduser2()),
                    chatsList.get(i).getKey()));
        }
        return list;
    }

//    public static TableChats tableChats(){
//
//    }

    public static List<TableFriends> tableFriends(List<GETFriend> friendList) {
        List<TableFriends> list = new ArrayList<>();
        for (int i = 0; i < friendList.size(); i++) {
            list.add(new TableFriends(Integer.parseInt(friendList.get(i).getIdfriends()),
                    Integer.parseInt(friendList.get(i).getIduser1()),
                    Integer.parseInt(friendList.get(i).getIduser2())));
        }
        return list;
    }

    public static TableFriends tableFriends(POSTFriend friend) {
        return new TableFriends(Integer.parseInt(friend.getIdfriend()),
                Integer.parseInt(friend.getIduser1()),
                Integer.parseInt(friend.getIduser2()));
    }

    public static List<TableMessages> tableMessages(List<GETMessage> messageList) {
        List<TableMessages> list = new ArrayList<>();
        for (int i = 0; i < messageList.size(); i++) {
            list.add(new TableMessages(Integer.parseInt(messageList.get(i).getIdmessage()),
                    Integer.parseInt(messageList.get(i).getIdchat()),
                    Integer.parseInt(messageList.get(i).getIduser()),
                    messageList.get(i).getContent()));
        }
        return list;
    }

    public static TableMessages tableMessages(POSTMessage message) {
        return new TableMessages(Integer.parseInt(message.getIdmessage()),
                Integer.parseInt(message.getIdchat()),
                Integer.parseInt(message.getIduser()),
                message.getContent());
    }

    public static TableProfile tableProfile(GETLogin login) {
        return new TableProfile(Integer.parseInt(login.getIduser()),
                login.getAvatar(),
                login.getNick(),
                login.getLogin(),
                login.getPassword());
    }

    public static TableProfile tableProfile(POSTUser login) {
        return new TableProfile(Integer.parseInt(login.getIduser()),
                login.getAvatar(),
                login.getNick(),
                login.getLogin(),
                login.getPassword());
    }

//    public static List<TableUsers> tableUsers(List<GETProfile> userList){
//        List<TableUsers> list = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//
//        }
//    }

    public static TableUsers tableUsers(GETProfile userList) {
        return new TableUsers(Integer.parseInt(userList.getIduser()),
                userList.getAvatar(),
                userList.getNick());
    }
}
