package kz.sgq.fs_imaytber.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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

    private MessageAdapter adapter;
    private DialogPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        init();
        presenter = new DialogPresenterImpl(this,
                getIntent().getIntExtra("idUser_1", 0),
                getIntent().getIntExtra("idUser_2", 0));
    }

    @OnClick(R.id.send)
    public void onClick(){
        presenter.handlerMessage();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
        getSupportActionBar().setTitle(nick);
    }

    @Override
    public void setAvatar(String avatar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
