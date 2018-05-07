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

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.ui.adapters.StikerAdapter;
import kz.sgq.fs_imaytber.util.Stikers;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedStikerListener;

public class StikerFragment extends Fragment {
    @BindView(R.id.stiker)
    RecyclerView stiker;

    private StikerAdapter stikerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stiker, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        stiker.setLayoutManager(horizontalLayoutManagaer);
        stiker.setAdapter(stikerAdapter);
        stikerAdapter.setOnSelectedStikerListener(stiker -> {
            OnSelectedStikerListener listener = (OnSelectedStikerListener) getActivity();
            listener.onClickStiker(stiker);
        });
    }

    public void createFragment(int idStiker) {
        if (stikerAdapter == null)
            stikerAdapter = new StikerAdapter();
        else
            stiker.scrollToPosition(0);

        switch (idStiker) {
            case 0:
                stikerAdapter.initStiker(Stikers.getOneStikerPack());
                break;
            case 1:
                stikerAdapter.initStiker(Stikers.getTwoStikerPack());
                break;
            case 2:
                stikerAdapter.initStiker(Stikers.getThreeStikerPack());
                break;
            case 3:
                stikerAdapter.initStiker(Stikers.getFourStikerPack());
                break;
            case 4:
                stikerAdapter.initStiker(Stikers.getFiveStikerPack());
                break;
        }
        stikerAdapter.notifyDataSetChanged();
    }
}
