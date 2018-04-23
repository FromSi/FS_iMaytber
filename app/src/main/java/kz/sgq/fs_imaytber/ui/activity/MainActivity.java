package kz.sgq.fs_imaytber.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.MainPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.MainPresenter;
import kz.sgq.fs_imaytber.mvp.view.MainView;
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
    private Dialog exit;

    private MainPresenter presenter;
    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Диалог");
        navigationView.getMenu().getItem(0).setChecked(true);
        setSupportActionBar(toolbar);
        init();
        initClickMenuNavigation();
        exit = initExitAccount();
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
                    navigationView.getMenu().getItem(0).setChecked(true);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Диалоги");
                    break;
                case R.id.friends:
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(true);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Друзья");
                    break;
                case R.id.settings:
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(true);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Настройки");
                    transaction.replace(R.id.content_frame, new SettingsFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.exit:
                    exit.show();
                    break;
            }
            drawerLayout.closeDrawers();
            return false;
        });
    }


}
