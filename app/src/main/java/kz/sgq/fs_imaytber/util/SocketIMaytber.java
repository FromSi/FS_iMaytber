package kz.sgq.fs_imaytber.util;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kz.sgq.fs_imaytber.infraestructure.networking.SocketService;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETChats;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETLogin;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETMessage;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTMessage;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTUser;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SocketIMaytber {
    private SocketService socket;

    private final String URL = "https://imaytber.herokuapp.com";

    public SocketIMaytber() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        socket = retrofit.create(SocketService.class);
    }

    public Observable<List<GETFriend>> getFriend(int iduser) {
        return socket.getFriend(iduser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GETProfile> getProfile(int iduser) {
        return socket.getProfile(iduser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GETLogin> getLogin(String login, String password) {
        return socket.getLogin(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<GETChats>> getChats(int iduser) {
        return socket.getChats(iduser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<GETMessage>> getMessage(int iduser) {
        return socket.getMessage(iduser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<POSTUser> postUser(String avatar, String nick,
                                         String login, String password,
                                         String token) {
        return socket.postUser(avatar, nick, login,
                password, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<POSTFriend> postFriend(int iduser_1, int iduser_2) {
        return socket.postFriend(iduser_1, iduser_2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<POSTMessage> postMessage(int iduser_1, int iduser_2,
                                               String content, String time) {
        return socket.postMessage(iduser_1, iduser_2, content, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable putNick(int iduser, String nick) {
        return socket.putNick(iduser, nick)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable putAvatar(int iduser, String avatar) {
        return socket.putAvatar(iduser, avatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable putToken(int iduser, String token) {
        return socket.putToken(iduser, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable putBio(int iduser, String bio) {
        return socket.putBio(iduser, bio)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable putPassword(String login, String password) {
        return socket.putPassword(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
