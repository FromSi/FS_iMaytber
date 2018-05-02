package kz.sgq.fs_imaytber.infraestructure.networking;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETChats;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETLogin;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETMessage;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.get.GETProfile;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTFriend;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTMessage;
import kz.sgq.fs_imaytber.infraestructure.networking.gson.post.POSTUser;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SocketService {
    @GET("/friend")
    Observable<List<GETFriend>> getFriend(@Query("iduser") int iduser);

    @GET("/profile")
    Observable<GETProfile> getProfile(@Query("iduser") int iduser);

    @GET("/login")
    Observable<GETLogin> getLogin(@Query("login") String login, @Query("password") String password);

    @GET("/chats")
    Observable<List<GETChats>> getChats(@Query("iduser") int iduser);

    @GET("/message")
    Observable<List<GETMessage>> getMessage(@Query("iduser") int iduser);

    @POST("/user")
    Observable<POSTUser> postUser(@Query("avatar") String avatar,
                                  @Query("nick") String nick,
                                  @Query("login") String login,
                                  @Query("password") String password,
                                  @Query("token") String token);

    @POST("/friend")
    Observable<POSTFriend> postFriend(@Query("iduser_1") int iduser_1, @Query("iduser_2") int iduser_2);

    @POST("/message")
    Observable<POSTMessage> postMessage(@Query("iduser_1") int iduser_1,
                                        @Query("iduser_2") int iduser_2,
                                        @Query("content") String content,
                                        @Query("time") String time);

    @PUT("/nick")
    Completable putNick(@Query("iduser") int iduser, @Query("nick") String nick);

    @PUT("/avatar")
    Completable putAvatar(@Query("iduser") int iduser, @Query("avatar") String avatar);

    @PUT("/token")
    Completable putToken(@Query("iduser") int iduser, @Query("token") String token);

    @PUT("/bio")
    Completable putBio(@Query("iduser") int iduser, @Query("bio") String bio);

    @PUT("/password")
    Completable putPassword(@Query("login") String login, @Query("password") String password);
}
