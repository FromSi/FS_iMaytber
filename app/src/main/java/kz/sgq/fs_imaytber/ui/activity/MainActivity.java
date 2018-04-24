package kz.sgq.fs_imaytber.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
    }

    private void initFragments() {
        friendFragment = new FriendFragment();
        settingsFragment = new SettingsFragment();
        if (getSharedPreferences("local", MODE_PRIVATE)
                .getBoolean("main_fragment", true)) {
            navigationView.getMenu().getItem(0).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Диалоги");
            stepFragments(settingsFragment);
        } else {
            navigationView.getMenu().getItem(1).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Друзья");
            stepFragments(friendFragment);
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
        presenter = new MainPresenterImpl(this);
    }

    private Dialog initExitAccount() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    presenter.exitActivity();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    // User cancelled the dialog
                });
        // Create the AlertDialog object and return it
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

        Intent intent = new Intent(this, BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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


}
