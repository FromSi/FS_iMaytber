package kz.sgq.fs_imaytber.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.room.table.TableUsers;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedDialogClick;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{
    private List<TableUsers> list = new ArrayList<>();
    private OnSelectedDialogClick dialogClick;

    public void addFriend(TableUsers tableUsers){
        list.add(tableUsers);
        notifyDataSetChanged();
    }

    public List<Integer> getIdUser(){
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            idList.add(list.get(i).getIdusers());
        }
        return idList;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        holder.setText(list.get(position).getNick(),
                list.get(position).getIdusers());
        holder.setAvatar(list.get(position).getAvatar());
        holder.itemClick.setOnClickListener(v -> dialogClick
                .onClick(list.get(position).getIdusers()));
    }

    public void setOnSelectedDialogClick(final OnSelectedDialogClick dialogClick){
        this.dialogClick = dialogClick;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nick)
        TextView nick;

        @BindView(R.id.idUser)
        TextView idUser;

        @BindView(R.id.avatar)
        CircleImageView avatar;

        @BindView(R.id.itemClick)
        CardView itemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setText(String nick, int idUser){
            this.nick.setText(nick);
            this.idUser.setText("#"+idUser);
        }

        private void setAvatar(String url){
            switch (url) {
                case "def1":
                    Picasso.get()
                            .load(R.drawable.def1)
                            .into(avatar);
                    break;
                case "def2":
                    Picasso.get()
                            .load(R.drawable.def2)
                            .into(avatar);
                    break;
                case "def3":
                    Picasso.get()
                            .load(R.drawable.def3)
                            .into(avatar);
                    break;
                case "def4":
                    Picasso.get()
                            .load(R.drawable.def4)
                            .into(avatar);
                    break;
                default:
                    Picasso.get()
                            .load(url)
                            .into(avatar);
                    break;
            }
        }
    }
}
