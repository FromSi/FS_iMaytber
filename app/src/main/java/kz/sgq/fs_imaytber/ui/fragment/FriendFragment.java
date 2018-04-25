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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private FriendAdapter adapter;

    private Dialog addFriend;
    private ProgressDialog loading;

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
        presenter = new FriendPresenterImpl(this);
    }

    @OnClick(R.id.fab)
    void onClick() {
        addFriend.show();
    }


    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FriendAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void onClickListenerAdapter() {
        adapter.setOnSelectedDialogClick(idUser -> presenter.startDialog(idUser));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void addFriend(TableUsers user) {
        adapter.addFriend(user);
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
}
