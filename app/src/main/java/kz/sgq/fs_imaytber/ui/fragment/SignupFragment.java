package kz.sgq.fs_imaytber.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.SignupPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.LoginPresenter;
import kz.sgq.fs_imaytber.mvp.view.SignupView;
import kz.sgq.fs_imaytber.ui.activity.MainActivity;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedButtonListener;

import static android.content.Context.MODE_PRIVATE;

public class SignupFragment extends Fragment implements SignupView {

    @BindView(R.id.nick)
    EditText nick;

    @BindView(R.id.login)
    EditText login;

    @BindView(R.id.password)
    EditText password;

    private final String TAG_PREF = "profile";
    private ProgressDialog loading;
    private LoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SignupPresenterImpl(this);
        loading = new ProgressDialog(view.getContext());
        loading.setMessage("Loading");
    }

    @OnClick({R.id.ok, R.id.change})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                presenter.handlerClick();
                break;
            case R.id.change:
                OnSelectedButtonListener listener = (OnSelectedButtonListener) getActivity();
                listener.onClickSignup();
                break;
        }
    }


    @Override
    public String getNick() {
        return nick.getText().toString();
    }

    @Override
    public String getLogin() {
        return login.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public String getToken() {
        String str = PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString("token", "1010101");
        Log.d("TagTest", str);
        return str;
    }

    @Override
    public void setupProfile() {
        Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE).edit()
                .putBoolean(TAG_PREF, false)
                .apply();
    }

    @Override
    public void showErrorNick() {
        nick.setError("Error");
    }

    @Override
    public void showErrorLogin() {
        login.setError("Error");
    }

    @Override
    public void showErrorPassword() {
        password.setError("Error");
    }

    @Override
    public void showErrorConnect() {
        Toast.makeText(getContext(), "No connect!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        loading.show();
    }

    @Override
    public void dismissProgressBar() {
        loading.dismiss();
    }

    @Override
    public void startActivity() {
        Objects.requireNonNull(getActivity()).getSharedPreferences("local", MODE_PRIVATE)
                .edit()
                .putBoolean("profile", true)
                .apply();

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
