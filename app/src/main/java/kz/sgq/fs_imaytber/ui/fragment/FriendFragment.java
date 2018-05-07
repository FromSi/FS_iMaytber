package kz.sgq.fs_imaytber.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.FriendPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.FriendPresenter;
import kz.sgq.fs_imaytber.mvp.view.FriendView;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.ui.activity.DialogActivity;
import kz.sgq.fs_imaytber.ui.adapters.FriendAdapter;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedDialogClick;

public class FriendFragment extends Fragment implements FriendView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nullItem)
    LinearLayout nullItem;

    private CircleImageView avatar;
    private TextView nick;
    private TextView login;
    private TextView bio;
    private Switch notif;

    private FriendAdapter adapter;

    private Dialog addFriend;
    private ProgressDialog loading;
    private Dialog friendDialog;
    private Dialog deleteDialog;

    private FriendPresenter presenter;
    private View view;

    private String textAddFriend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading = new ProgressDialog(view.getContext());
        loading.setMessage(getResources().getString(R.string.loading));
        addFriend = addFriendDialog();
        init();
        onClickListenerAdapter();
    }

    private Dialog createDialogDelete(int idUser){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_friend_dialog)
                .setPositiveButton(R.string.delete_friend, (dialog, id) ->                     presenter.deleteFriend(idUser))
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    private Dialog dialogFriend(int idUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_user, null);
        avatar = view.findViewById(R.id.avatar);
        nick = view.findViewById(R.id.nick);
        login = view.findViewById(R.id.login);
        bio = view.findViewById(R.id.bio);
        notif = view.findViewById(R.id.notif);
        presenter.getUser(idUser);
        notif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (notif.isChecked())
                presenter.setNotif(idUser, true);
            else
                presenter.setNotif(idUser, false);
        });
        builder.setView(view)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                });
        AlertDialog dialog = builder.create();
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            dialog.dismiss();
            presenter.startDialog(idUser);
        });
        return dialog;
    }

    @OnClick(R.id.fab)
    void onClick() {
        addFriend.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter = new FriendPresenterImpl(this);
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FriendAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void onClickListenerAdapter() {
        adapter.setOnSelectedDialogClick(new OnSelectedDialogClick() {
            @Override
            public void onClick(int idUser) {

            }

            @Override
            public void onClickDialog(int idUser) {
                friendDialog = dialogFriend(idUser);
                friendDialog.show();
            }

            @Override
            public void onClickDelete(int idUser) {
                deleteDialog = createDialogDelete(idUser);
                deleteDialog.show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onDestroy();
    }

    @Override
    public void addFriend(TableUsers user) {
        nullItem.setVisibility(View.GONE);
        adapter.addFriend(user);
    }

    @Override
    public void deleteFriend(int idUser) {
        adapter.deleteFriend(idUser);
    }

    @Override
    public void showErrorAddFriend() {
        Snackbar.make(view, getResources().getString(R.string.snacbar_add_friend), Snackbar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.repeat), v -> handlerAddFriend())
                .show();
    }

    @Override
    public List<Integer> getIdUsers() {
        return adapter.getIdUser();
    }

    private Dialog addFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_add_friend, null);
        EditText text = view.findViewById(R.id.idUser);
        builder.setView(view)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    if (!text.getText().toString().isEmpty() &&
                            Integer.parseInt(text.getText().toString()) >= 1) {
                        textAddFriend = text.getText().toString();
                        handlerAddFriend();
                        text.setText("");
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    private void handlerAddFriend() {
        loading.show();
        presenter.addFriend(Integer.parseInt(textAddFriend));
    }

    @Override
    public void dismissProgressBar() {
        loading.dismiss();
    }

    @Override
    public void startDialog(int idUser_1, int idUser_2) {
        Intent intent = new Intent(getContext(), DialogActivity.class);
        intent.putExtra("idUser_1", idUser_1);
        intent.putExtra("idUser_2", idUser_2);
        startActivity(intent);
    }

    @Override
    public void showNullItem() {
        nullItem.setVisibility(View.VISIBLE);

    }

    @Override
    public void setDialogUser(String avatar, String nick, String login,
                              String bio, boolean switchBool) {
        switch (avatar) {
            case "def1":
                this.avatar.setImageDrawable(getResources().getDrawable(R.drawable.def1));
                break;
            case "def2":
                this.avatar.setImageDrawable(getResources().getDrawable(R.drawable.def2));
                break;
            case "def3":
                this.avatar.setImageDrawable(getResources().getDrawable(R.drawable.def3));
                break;
            case "def4":
                this.avatar.setImageDrawable(getResources().getDrawable(R.drawable.def4));
                break;
            default:
                Picasso.get()
                        .load(avatar)
                        .into(this.avatar);
                break;
        }
        this.nick.setText(nick);
        this.login.setText(login);
        this.notif.setChecked(switchBool);
        if (bio != null)
            this.bio.setText(bio);
    }
}
