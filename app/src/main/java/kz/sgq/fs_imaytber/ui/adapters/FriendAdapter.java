package kz.sgq.fs_imaytber.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.room.table.TableUsers;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{
    private List<TableUsers> list = new ArrayList<>();

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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setText(String nick, int idUser){
            this.nick.setText(nick);
            this.idUser.setText("#"+idUser);
        }
    }
}
