package kz.sgq.fs_imaytber.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.room.table.TableUsers;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private List<TableMessages> list = new ArrayList<>();
    private int idUser;

    public void setIdUser(int idUser){
        this.idUser = idUser;
    }

    public void addMessage(TableMessages message){
        list.add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        holder.setContent(list.get(position).getContent());
        if (idUser == list.get(position).getIduser())
            holder.setLContent(true);
        else
            holder.setLContent(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.l_content)
        LinearLayout l_content;
        @BindView(R.id.item)
        LinearLayout item;
        @BindView(R.id.photo)
        ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setContent(String content){
            this.content.setText(content);
        }

        private void setLContent(boolean bool){
            if (bool) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginEnd((int) itemView.getResources().getDimension(R.dimen.f_content_layout_marginStart));
                layoutParams.setMarginStart((int) itemView.getResources().getDimension(R.dimen.f_content_layout_marginEnd));
                l_content.setLayoutParams(layoutParams);
                l_content.setGravity(Gravity.START);
            } else {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginEnd((int) itemView.getResources().getDimension(R.dimen.f_content_layout_marginEnd));
                layoutParams.setMarginStart((int) itemView.getResources().getDimension(R.dimen.f_content_layout_marginStart));
                l_content.setLayoutParams(layoutParams);
                l_content.setGravity(Gravity.END);
            }
        }

    }
}
