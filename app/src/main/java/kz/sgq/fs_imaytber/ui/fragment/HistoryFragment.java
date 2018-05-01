package kz.sgq.fs_imaytber.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.HistoryPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.HistoryPresenter;
import kz.sgq.fs_imaytber.mvp.view.HistoryView;
import kz.sgq.fs_imaytber.ui.activity.DialogActivity;
import kz.sgq.fs_imaytber.ui.adapters.HistoryAdapter;
import kz.sgq.fs_imaytber.util.HistoryZIP;

public class HistoryFragment extends Fragment implements HistoryView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nullItem)
    LinearLayout nullItem;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private View view;
    private HistoryPresenter presenter;

    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        init();
        onClickListenerAdapter();
        Log.d("onCreateView123","onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter = new HistoryPresenterImpl(this);
        Log.d("onCreateView123","onStart");
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void onClick(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("onCreateView123","onViewCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DestroyPause","onDestroy");
        presenter.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("DestroyPause","onPause");
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


    private void onClickListenerAdapter() {
        adapter.setOnSelectedDialogClick(idUser -> presenter.startDialog(idUser));
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
