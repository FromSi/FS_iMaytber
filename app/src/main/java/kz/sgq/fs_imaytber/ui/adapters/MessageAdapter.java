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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.util.Message;
import kz.sgq.fs_imaytber.util.MessageCondition;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> list = new ArrayList<>();
    private List<MessageCondition> conditionList = new ArrayList<>();
    private int idUser;

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int addMessage(Message message) {
        list.add(message);
        conditionList.add(MessageCondition.LOADING);
        notifyDataSetChanged();
        return list.size() - 1;
    }

    public void uploadCondition(int idMessage, MessageCondition condition) {
        conditionList.set(idMessage, condition);
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
        if (idUser == list.get(position).getIdUser())
            holder.setLContent(true);
        else
            holder.setLContent(false);
        holder.setCondition(conditionList.get(position));
        holder.setTime(list.get(position).getTime());
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
        @BindView(R.id.condition)
        ImageView condition;
        @BindView(R.id.s_time)
        TextView s_time;
        @BindView(R.id.m_time)
        TextView m_time;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setCondition(MessageCondition condition) {
            switch (condition) {
                case DONE:
                    this.condition.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.done));
                    break;
                case ERROR:
                    this.condition.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.error));
                    break;
                case LOADING:
                    this.condition.setImageDrawable(itemView.getResources()
                            .getDrawable(R.drawable.loading));
                    break;
            }
        }

        private void setContent(String content) {
            this.content.setText(content);
        }

        private void setLContent(boolean bool) {
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

        private void setTime(String time){
//            String pattern = "H:mm";
//            SimpleDateFormat format = new SimpleDateFormat(pattern);
//            s_time.setText(format.format(Date.parse(time)));
//            m_time.setText(format.format(Date.parse(time)));
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy", Locale.ROOT);
            Date newDate = null;
            try {
                newDate = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("H:mm");
            s_time.setText(format.format(newDate));
            m_time.setText(format.format(newDate));
        }

    }
}
