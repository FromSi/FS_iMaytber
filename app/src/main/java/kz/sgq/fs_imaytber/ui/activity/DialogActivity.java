package kz.sgq.fs_imaytber.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.DialogPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.DialogPresenter;
import kz.sgq.fs_imaytber.mvp.view.DialogView;
import kz.sgq.fs_imaytber.ui.adapters.MessageAdapter;
import kz.sgq.fs_imaytber.ui.fragment.StikerFragment;
import kz.sgq.fs_imaytber.util.Message;
import kz.sgq.fs_imaytber.util.MessageCondition;
import kz.sgq.fs_imaytber.util.interfaces.OnClickListenerDelete;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedStikerListener;

public class DialogActivity extends AppCompatActivity implements DialogView, OnSelectedStikerListener {

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
    @BindView(R.id.stiker)
    FrameLayout stiker;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.l_stiker)
    LinearLayout l_stiker;
    @BindString(R.string.loading)
    String loadingText;
    @BindString(R.string.error_delete_text)
    String error_delete_text;

    private Dialog friendDialog;
    private Dialog deleteDialog;

    private CircleImageView avatarDialog;
    private TextView nick;
    private TextView login;
    private TextView bio;
    private Switch notif;

    private MessageAdapter adapter;
    private DialogPresenter presenter;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        loading = new ProgressDialog(this);
        loading.setMessage(loadingText);
        fab.hide(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        setupViewPager();
        presenter = new DialogPresenterImpl(this,
                getIntent().getIntExtra("idUser_1", 0),
                getIntent().getIntExtra("idUser_2", 0));
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("notifId", getIntent().getIntExtra("idUser_2", 0))
                .apply();
        showDialogDeleteMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("notifId", 0)
                .apply();
    }

    void showDialogDeleteMessage(){
        adapter.setOnClickListenerDelete(idMessage -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete_message_dialog)
                    .setPositiveButton(R.string.delete_friend, (dialog, id) -> {
                        presenter.deleteMessage(idMessage);
                        loading.show();
                    })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    });
            Dialog dialog = builder.create();
            dialog.show();
        });
    }

    private void setupViewPager() {
        for (int i = 0; i < 5; i++) {
            try {
                InputStream ims = getAssets().open("stiker_" + i + "_00.jpeg");
                Drawable d = Drawable.createFromStream(ims, null);
                tabLayout.addTab(tabLayout.newTab().setIcon(d));
            } catch (IOException ex) {
            }
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        StikerFragment stikerFragment = new StikerFragment();
        stikerFragment.createFragment(0);
        transaction.replace(R.id.stiker, stikerFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                stikerFragment.createFragment(tab.getPosition());
                transaction.replace(R.id.stiker, stikerFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.add_friend:
                presenter.addFriend();
                return true;
            case R.id.delete_friend:
                deleteDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Dialog dialogFriend(int idUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_user, null);
        avatarDialog = view.findViewById(R.id.avatar);
        nick = view.findViewById(R.id.nick);
        login = view.findViewById(R.id.login);
        bio = view.findViewById(R.id.bio);
        notif = view.findViewById(R.id.notif);
        notif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (notif.isChecked())
                presenter.setNotif(idUser, true);
            else
                presenter.setNotif(idUser, false);
        });
        builder.setView(view)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                });
        AlertDialog dialog = builder.create();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.hide();
        return dialog;
    }

    @OnClick(R.id.send)
    public void onClick() {
        presenter.handlerReadMessage();
    }

    @OnClick(R.id.avatar)
    public void onClickAvatar() {
        presenter.getUser();
    }

    @OnClick(R.id.b_stiker)
    public void onClickStiker() {
        if (l_stiker.getVisibility() == View.VISIBLE)
            l_stiker.setVisibility(View.GONE);
        else
            l_stiker.setVisibility(View.VISIBLE);
    }

    private Dialog createDialogDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_friend_dialog)
                .setPositiveButton(R.string.delete_friend, (dialog, id) -> presenter.deleteFriend())
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }

    private void init() {
        deleteDialog = createDialogDelete();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.getItemCount() - 1 ==
                        linearLayoutManager.findLastVisibleItemPosition())
                    fab.hide();
                else
                    fab.show();
            }
        });
    }

    @OnClick(R.id.fab)
    public void scrollDown() {
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void setDialogUser(String avatar, String nick, String login, String bio, boolean switchBool) {
        switch (avatar) {
            case "def1":
                this.avatarDialog.setImageDrawable(getResources().getDrawable(R.drawable.def1));
                break;
            case "def2":
                this.avatarDialog.setImageDrawable(getResources().getDrawable(R.drawable.def2));
                break;
            case "def3":
                this.avatarDialog.setImageDrawable(getResources().getDrawable(R.drawable.def3));
                break;
            case "def4":
                this.avatarDialog.setImageDrawable(getResources().getDrawable(R.drawable.def4));
                break;
            default:
                Picasso.get()
                        .load(avatar)
                        .into(this.avatar);
                break;
        }
        this.nick.setText(nick);
        this.login.setText(login);
        this.notif.setChecked(switchBool);
        if (bio != null)
            this.bio.setText(bio);
    }

    @Override
    public void showDialogUser(int idUser) {
        friendDialog = dialogFriend(idUser);
        friendDialog.show();
    }

    @Override
    public void setIdUserAdapter(int id) {
        adapter.setIdUser(id);
    }

    @Override
    public int addMessage(Message messages) {
        int idMessage = adapter.addMessage(messages);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        return idMessage;
    }

    @Override
    public String getText() {
        String str = text.getText().toString();
        text.setText("");
        return str;
    }

    @Override
    public void showDeleteFriend() {
        Toast.makeText(this, getResources().getString(R.string.toast_delete_friend),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddFriend() {
        Toast.makeText(this, getResources().getString(R.string.toast_add_friend),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        loading.dismiss();
    }

    @Override
    public void showErrorDelete() {
        Toast.makeText(this, error_delete_text, Toast.LENGTH_SHORT).show();
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
    public void uploadCondition(int idMessage, MessageCondition condition) {
        adapter.uploadCondition(idMessage, condition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onClickStiker(String stiker) {
        text.setText(stiker);
        presenter.handlerReadMessage();
    }
}
