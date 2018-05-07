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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.util.HistoryZIP;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedDialogClick;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryZIP> messageList = new ArrayList<>();
    private OnSelectedDialogClick dialogClick;

    public void addHistory(HistoryZIP message) {
        this.messageList.add(message);
        notifyDataSetChanged();
    }

    public void clearHistory() {
        messageList.clear();
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        return new ViewHolder(view);
    }

    public void setOnSelectedDialogClick(final OnSelectedDialogClick dialogClick) {
        this.dialogClick = dialogClick;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.setAvatar(messageList.get(position).getAvatar());
        holder.setNick(messageList.get(position).getNick());
        holder.setContent(messageList.get(position).getContent());
        holder.itemClick.setOnClickListener(v -> dialogClick
                .onClick(messageList.get(position).getIdUser()));
        holder.setTime(messageList.get(position).getTime());
        holder.avatar.setOnClickListener(v ->
                dialogClick.onClickDialog(messageList.get(position).getIdUser())
        );
        holder.setRead(messageList.get(position).getRead());
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
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.read)
        TextView read;
        @BindView(R.id.c_read)
        CardView c_read;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setRead(int read) {
            if (read > 0) {
                this.read.setText(String.valueOf(read));
                this.c_read.setVisibility(View.VISIBLE);
            } else {
                this.read.setText(String.valueOf(0));
                this.c_read.setVisibility(View.GONE);
            }
        }

        private void setNick(String nick) {
            this.nick.setText(nick);
        }

        private void setAvatar(String avatar) {
            switch (avatar) {
                case "def1":
                    this.avatar.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.def1));
                    break;
                case "def2":
                    this.avatar.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.def2));
                    break;
                case "def3":
                    this.avatar.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.def3));
                    break;
                case "def4":
                    this.avatar.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.def4));
                    break;
                default:
                    Picasso.get()
                            .load(avatar)
                            .into(this.avatar);
                    break;
            }
        }

        private void setContent(String content) {
            if (content.length() == 11) {
                if (content.substring(0, content.length() - 4).equals("stiker_")) {
                    this.content.setText("[Стикер]");
                } else {
                    this.content.setText(content);
                }
            } else {
                this.content.setText(content);
            }
        }

        private void setTime(String time) {
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy",
                    Locale.ROOT);
            Date newDate = null;
            try {
                newDate = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("H:mm");
            this.time.setText(format.format(newDate));
        }
    }
}
