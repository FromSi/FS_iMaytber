package kz.sgq.fs_imaytber.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.MainPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.MainPresenter;
import kz.sgq.fs_imaytber.mvp.view.MainView;
import kz.sgq.fs_imaytber.ui.fragment.FriendFragment;
import kz.sgq.fs_imaytber.ui.fragment.HistoryFragment;
import kz.sgq.fs_imaytber.ui.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View headerNavigation;
    private TextView nick;
    private TextView login;
    private CircleImageView avatar;
    private Dialog exit;

    private HistoryFragment historyFragment;
    private FriendFragment friendFragment;
    private SettingsFragment settingsFragment;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        init();
        initFragments();
        initClickMenuNavigation();
        exit = initExitAccount();
        presenter = new MainPresenterImpl(this);
    }

    private void initFragments() {
        historyFragment = new HistoryFragment();
        friendFragment = new FriendFragment();
        settingsFragment = new SettingsFragment();
        if (getIntent().getBooleanExtra("fragment", false)){
            navigationView.getMenu().getItem(1).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Друзья");
            stepFragments(friendFragment);
        } else {
            if (getSharedPreferences("local", MODE_PRIVATE)
                    .getBoolean("main_fragment", true)) {
                navigationView.getMenu().getItem(0).setChecked(true);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Диалоги");
                stepFragments(historyFragment);
            } else {
                navigationView.getMenu().getItem(1).setChecked(true);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Друзья");
                stepFragments(friendFragment);
            }
        }
    }

    private void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        headerNavigation = navigationView.getHeaderView(0);
        nick = headerNavigation.findViewById(R.id.nick);
        login = headerNavigation.findViewById(R.id.login);
        avatar = headerNavigation.findViewById(R.id.avatar);
    }

    private Dialog initExitAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    presenter.exitActivity();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    // User cancelled the dialog
                });
        return builder.create();
    }

    @Override
    public void setNick(String nick) {
        this.nick.setText(nick);
    }

    @Override
    public void setLogin(String login) {
        this.login.setText(login);
    }

    @Override
    public void setAvatar(String url) {
        switch (url) {
            case "def1":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def1));
                break;
            case "def2":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def2));
                break;
            case "def3":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def3));
                break;
            case "def4":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def4));
                break;
            default:
                Picasso.get()
                        .load(url)
                        .into(avatar);
                break;
        }
    }

    @Override
    public void exitActivity() {
        getSharedPreferences("local", MODE_PRIVATE)
                .edit()
                .putBoolean("profile", false)
                .apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showErrorExit() {
        Toast.makeText(this, getResources().getString(R.string.error_exit),
                Toast.LENGTH_SHORT).show();
    }

    private void initClickMenuNavigation() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.message:
                    if (!item.isChecked()) {
                        navigationView.getMenu().getItem(0).setChecked(true);
                        navigationView.getMenu().getItem(1).setChecked(false);
                        navigationView.getMenu().getItem(2).setChecked(false);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Диалоги");
                        stepFragments(historyFragment);
                    }
                    break;
                case R.id.friends:
                    if (!item.isChecked()) {
                        navigationView.getMenu().getItem(0).setChecked(false);
                        navigationView.getMenu().getItem(1).setChecked(true);
                        navigationView.getMenu().getItem(2).setChecked(false);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Друзья");
                        stepFragments(friendFragment);
                    }
                    break;
                case R.id.settings:
                    if (!item.isChecked()) {
                        navigationView.getMenu().getItem(0).setChecked(false);
                        navigationView.getMenu().getItem(1).setChecked(false);
                        navigationView.getMenu().getItem(2).setChecked(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Настройки");
                        stepFragments(settingsFragment);
                    }
                    break;
                case R.id.exit:
                    exit.show();
                    break;
            }
            drawerLayout.closeDrawers();
            return false;
        });
    }

    private void stepFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
