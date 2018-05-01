package kz.sgq.fs_imaytber.ui.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
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
    private List<Boolean> visibleList = new ArrayList<>();
    private int idUser;

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int addMessage(Message message) {
        list.add(message);
        conditionList.add(MessageCondition.LOADING);
        if (handlerVisibly(message.getContent()))
            visibleList.add(true);
        else
            visibleList.add(false);
        notifyDataSetChanged();
        return list.size() - 1;
    }

    private boolean handlerVisibly(String message){
        if (message.length() == 11){
            if (message.substring(0, message.length()-4).equals("stiker_"))
                return false;
        } else {
            return true;
        }
        return true;
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

        if (visibleList.get(position)) {
            holder.setContent(list.get(position).getContent());
            holder.setContainer(true);
        } else {
            holder.setStiker(list.get(position).getContent());
            holder.setContainer(false);
        }
        holder.setCondition(conditionList.get(position));
        holder.setTime(list.get(position).getTime());
        if (idUser == list.get(position).getIdUser())
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
        @BindView(R.id.stiker)
        ImageView stiker;
        @BindView(R.id.condition)
        ImageView condition;
        @BindView(R.id.s_time)
        TextView s_time;
        @BindView(R.id.m_time)
        TextView m_time;
        @BindView(R.id.containerOne)
        LinearLayout containerOne;
        @BindView(R.id.containerTwo)
        CardView containerTwo;

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

        private void setContainer(boolean bool) {
            if (!bool) {
                containerOne.setVisibility(View.VISIBLE);
                containerTwo.setVisibility(View.GONE);
            } else {
                containerTwo.setVisibility(View.VISIBLE);
                containerOne.setVisibility(View.GONE);
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

        private void setTime(String time) {
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
