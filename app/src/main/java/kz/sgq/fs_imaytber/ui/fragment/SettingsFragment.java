package kz.sgq.fs_imaytber.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.SettingsPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.SettingsPresenter;
import kz.sgq.fs_imaytber.mvp.view.SettingsView;

public class SettingsFragment extends Fragment implements SettingsView {

    @BindView(R.id.nickText)
    TextView nickText;

    @BindView(R.id.loginText)
    TextView loginText;

    @BindView(R.id.idText)
    TextView idText;

    @BindView(R.id.nick)
    EditText nick;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.radioDialogs)
    RadioButton radioDialogs;

    @BindView(R.id.radioFriends)
    RadioButton radioFriends;

    private SettingsPresenter presenter;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SettingsPresenterImpl(this);
        preferences = Objects.requireNonNull(getContext())
                .getSharedPreferences("local", Context.MODE_PRIVATE);
        init();
    }

    private void init() {
        if (preferences.getBoolean("main_fragment", true))
            radioDialogs.setChecked(true);
        else
            radioFriends.setChecked(true);
    }

    @OnClick(R.id.save)
    void onClick() {
        if (nick.length() >= 5)
            presenter.onClickNick(nick.getText().toString());
        if (password.length() >= 5)
            presenter.onClickPassword(password.getText().toString());
    }

    @OnCheckedChanged({R.id.radioDialogs, R.id.radioFriends})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            switch (button.getId()) {
                case R.id.radioDialogs:
                    preferences.edit()
                            .putBoolean("main_fragment", true)
                            .apply();
                    break;
                case R.id.radioFriends:
                    preferences.edit()
                            .putBoolean("main_fragment", false)
                            .apply();
                    break;
            }
        }
    }


    @Override
    public void setProfile(String nick, String login, String id) {
        nickText.setText(nick);
        loginText.setText(login);
        idText.setText(id);
    }

    @Override
    public void setNick(String nick) {
        nickText.setText(nick);
    }

    @Override
    public void setPassword() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
