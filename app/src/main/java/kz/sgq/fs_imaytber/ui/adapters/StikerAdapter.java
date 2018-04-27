package kz.sgq.fs_imaytber.ui.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;

public class StikerAdapter extends RecyclerView.Adapter<StikerAdapter.ViewHolder> {
    private List<String> list = new ArrayList<>();

    public StikerAdapter() {
        for (int i = 0; i < 16; i++) {
            list.add(":stiker_" + i + ":");
        }
    }

    @NonNull
    @Override
    public StikerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stiker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StikerAdapter.ViewHolder holder, int position) {
        holder.setStiker(position);
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

        private void setStiker(int i) {
            switch (i) {
                case 0:
                    Picasso.get().load(R.drawable.stiker_0).into(stiker);
                    break;
                case 1:
                    Picasso.get().load(R.drawable.stiker_1).into(stiker);
                    break;
                case 2:
                    Picasso.get().load(R.drawable.stiker_2).into(stiker);
                    break;
                case 3:
                    Picasso.get().load(R.drawable.stiker_3).into(stiker);
                    break;
                case 4:
                    Picasso.get().load(R.drawable.stiker_4).into(stiker);
                    break;
                case 5:
                    Picasso.get().load(R.drawable.stiker_5).into(stiker);
                    break;
                case 6:
                    Picasso.get().load(R.drawable.stiker_6).into(stiker);
                    break;
                case 7:
                    Picasso.get().load(R.drawable.stiker_7).into(stiker);
                    break;
                case 8:
                    Picasso.get().load(R.drawable.stiker_8).into(stiker);
                    break;
                case 9:
                    Picasso.get().load(R.drawable.stiker_9).into(stiker);
                    break;
                case 10:
                    Picasso.get().load(R.drawable.stiker_10).into(stiker);
                    break;
                case 11:
                    Picasso.get().load(R.drawable.stiker_11).into(stiker);
                    break;
                case 12:
                    Picasso.get().load(R.drawable.stiker_12).into(stiker);
                    break;
                case 13:
                    Picasso.get().load(R.drawable.stiker_13).into(stiker);
                    break;
                case 14:
                    Picasso.get().load(R.drawable.stiker_14).into(stiker);
                    break;
                case 15:
                    Picasso.get().load(R.drawable.stiker_15).into(stiker);
                    break;
            }
        }
    }
}
