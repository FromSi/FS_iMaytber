package kz.sgq.fs_imaytber.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.FriendPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.FriendPresenter;
import kz.sgq.fs_imaytber.mvp.view.FriendView;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.ui.adapters.FriendAdapter;

public class FriendFragment extends Fragment implements FriendView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FriendAdapter adapter;

    private Dialog addFriend;
    private ProgressDialog loading;

    private FriendPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading = new ProgressDialog(view.getContext());
        loading.setMessage("Loading");
        presenter = new FriendPresenterImpl(this);
        addFriend = addFriendDialog(getContext());
        init(view.getContext());
    }

    @OnClick(R.id.fab)
    void onClick() {
        addFriend.show();
    }


    private void init(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FriendAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addFriend(TableUsers user) {
        adapter.addFriend(user);
    }

    @Override
    public void showErrorAddFriend() {

    }

    @Override
    public List<Integer> getIdUsers() {
        return adapter.getIdUser();
    }

    private Dialog addFriendDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_friend, null);
        EditText text = view.findViewById(R.id.idUser);
        builder.setView(view)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    if (!text.getText().toString().isEmpty() &&
                            Integer.parseInt(text.getText().toString()) >= 1) {
                        loading.show();
                        presenter.addFriend(Integer.parseInt(text.getText().toString()));
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    @Override
    public void dismissProgressBar() {
        loading.dismiss();
    }
}
