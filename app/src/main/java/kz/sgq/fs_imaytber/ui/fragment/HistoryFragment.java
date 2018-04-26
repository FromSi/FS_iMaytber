package kz.sgq.fs_imaytber.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.HistoryPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.HistoryPresenter;
import kz.sgq.fs_imaytber.mvp.view.HistoryView;
import kz.sgq.fs_imaytber.ui.adapters.HistoryAdapter;
import kz.sgq.fs_imaytber.util.HistoryZIP;

public class HistoryFragment extends Fragment implements HistoryView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private View view;
    private HistoryPresenter presenter;

    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
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
        presenter = new HistoryPresenterImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void addHistory(HistoryZIP message) {
        adapter.addHistory(message);
    }

    @Override
    public void clearHistory() {
    adapter.clearHistory();
    }
}
