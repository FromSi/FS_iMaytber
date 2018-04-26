package kz.sgq.fs_imaytber.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.DialogPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.DialogPresenter;
import kz.sgq.fs_imaytber.mvp.view.DialogView;
import kz.sgq.fs_imaytber.room.table.TableMessages;
import kz.sgq.fs_imaytber.ui.adapters.MessageAdapter;

public class DialogActivity extends AppCompatActivity implements DialogView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text)
    EditText text;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.avatar)
    ImageView avatar;

    private MessageAdapter adapter;
    private DialogPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        presenter = new DialogPresenterImpl(this,
                getIntent().getIntExtra("idUser_1", 0),
                getIntent().getIntExtra("idUser_2", 0));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.send)
    public void onClick(){
        presenter.handlerMessage();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setIdUserAdapter(int id) {
        adapter.setIdUser(id);
    }

    @Override
    public void addMessage(TableMessages messages) {
        adapter.addMessage(messages);
    }

    @Override
    public String getText() {
        String str = text.getText().toString();
        text.setText("");
        return str;
    }

    @Override
    public void hideHandler() {

    }

    @Override
    public void errorMessage() {

    }

    @Override
    public void setToolBar(String nick) {
        title.setText(nick);
    }

    @Override
    public void setAvatar(String avatar) {
        switch (avatar) {
            case "def1":
                this.avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def1));
                break;
            case "def2":
                this.avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def2));
                break;
            case "def3":
                this.avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def3));
                break;
            case "def4":
                this.avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def4));
                break;
            default:
                Picasso.get()
                        .load(avatar)
                        .into(this.avatar);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
