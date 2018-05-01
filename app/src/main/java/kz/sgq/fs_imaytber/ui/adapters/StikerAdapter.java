package kz.sgq.fs_imaytber.ui.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedStikerListener;

public class StikerAdapter extends RecyclerView.Adapter<StikerAdapter.ViewHolder> {
    private List<String> list = new ArrayList<>();
    private OnSelectedStikerListener stikerListener;

    public void initStiker(List<String> list) {
        this.list = list;
    }

    public void setOnSelectedStikerListener(OnSelectedStikerListener stikerListener) {
        this.stikerListener = stikerListener;
    }

    @NonNull
    @Override
    public StikerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stiker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StikerAdapter.ViewHolder holder, int position) {
        holder.setStiker(list.get(position));
        holder.stiker.setOnClickListener(v -> stikerListener.onClickStiker(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stiker)
        ImageView stiker;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setStiker(String stiker) {
            try {
                InputStream ims = itemView.getContext().getAssets().open(stiker + ".jpeg");
                Drawable d = Drawable.createFromStream(ims, null);
                this.stiker.setImageDrawable(d);
            } catch (IOException ex) {
            }
        }
    }
}
