package kz.sgq.fs_imaytber.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.HistoryPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.HistoryPresenter;
import kz.sgq.fs_imaytber.mvp.view.HistoryView;
import kz.sgq.fs_imaytber.ui.activity.DialogActivity;
import kz.sgq.fs_imaytber.ui.adapters.HistoryAdapter;
import kz.sgq.fs_imaytber.util.HistoryZIP;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedDialogClick;

public class HistoryFragment extends Fragment implements HistoryView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nullItem)
    LinearLayout nullItem;

    private CircleImageView avatar;
    private TextView nick;
    private TextView login;
    private TextView bio;
    private Switch notif;

    private Dialog friendDialog;

    private View view;
    private HistoryPresenter presenter;

    private HistoryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        init();
        onClickListenerAdapter();
        Log.d("onCreateView123", "onCreateView");
        return view;
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

    @Override
    public void onStart() {
        super.onStart();
        presenter = new HistoryPresenterImpl(this);
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onDestroy();
    }

    @Override
    public void addHistory(HistoryZIP message) {
        nullItem.setVisibility(View.GONE);
        adapter.addHistory(message);
    }

    @Override
    public void clearHistory() {
        adapter.clearHistory();
    }

    @Override
    public void setPosition() {
        if (linearLayoutManager.getItemCount() - 2 ==
                linearLayoutManager.findLastVisibleItemPosition()) {
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    @Override
    public void setDialogUser(String avatar, String nick, String login, String bio, boolean switchBool) {
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


    private void onClickListenerAdapter() {
        adapter.setOnSelectedDialogClick(new OnSelectedDialogClick() {
            @Override
            public void onClick(int idUser) {
                presenter.startDialog(idUser);
            }

            @Override
            public void onClickDialog(int idUser) {
                friendDialog = dialogFriend(idUser);
                friendDialog.show();
            }

            @Override
            public void onClickDelete(int idUser) {

            }
        });
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
}
