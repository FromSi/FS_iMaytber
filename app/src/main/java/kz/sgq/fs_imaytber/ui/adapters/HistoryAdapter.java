package kz.sgq.fs_imaytber.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.HistoryZIP;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedDialogClick;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryZIP> messageList = new ArrayList<>();
    private OnSelectedDialogClick dialogClick;

    public void addHistory(HistoryZIP message){
        this.messageList.add(message);
        notifyDataSetChanged();
    }

    public void clearHistory(){
        messageList.clear();
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        return new ViewHolder(view);
    }

    public void setOnSelectedDialogClick(final OnSelectedDialogClick dialogClick){
        this.dialogClick = dialogClick;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.setAvatar(messageList.get(position).getAvatar());
        holder.setNick(messageList.get(position).getNick());
        holder.setContent(messageList.get(position).getContent());
        holder.itemClick.setOnClickListener(v -> dialogClick
                .onClick(messageList.get(position).getIdUser()));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.nick)
        TextView nick;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.itemClick)
        CardView itemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setNick(String nick){
            this.nick.setText(nick);
        }

        private void setAvatar (String avatar){
            switch (avatar) {
                case "def1":
                    Picasso.get()
                            .load(R.drawable.def1)
                            .into(this.avatar);
                    break;
                case "def2":
                    Picasso.get()
                            .load(R.drawable.def2)
                            .into(this.avatar);
                    break;
                case "def3":
                    Picasso.get()
                            .load(R.drawable.def3)
                            .into(this.avatar);
                    break;
                case "def4":
                    Picasso.get()
                            .load(R.drawable.def4)
                            .into(this.avatar);
                    break;
                default:
                    Picasso.get()
                            .load(avatar)
                            .into(this.avatar);
                    break;
            }
        }

        private void setContent(String content){
            this.content.setText(content);
        }
    }
}
