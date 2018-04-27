package kz.sgq.fs_imaytber.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.mvp.presenter.LoginPresenterImpl;
import kz.sgq.fs_imaytber.mvp.presenter.interfaces.LoginPresenter;
import kz.sgq.fs_imaytber.mvp.view.LoginView;
import kz.sgq.fs_imaytber.ui.activity.MainActivity;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedButtonListener;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment implements LoginView {

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;

    private ProgressDialog loading;
    private LoginPresenter presenter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ok, R.id.change})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                presenter.handlerClick();
                break;
            case R.id.change:
                OnSelectedButtonListener listener = (OnSelectedButtonListener) getActivity();
                listener.onClickLogin();
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new LoginPresenterImpl(this);
        loading = new ProgressDialog(view.getContext());
        loading.setMessage("Loading");
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
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("token", "not");
    }

    @Override
    public void showErrorLogin() {
        login.setError(getResources().getString(R.string.error_login));
    }

    @Override
    public void showErrorPassword() {
        password.setError(getResources().getString(R.string.error_password));
    }

    @Override
    public void showErrorConnect() {
        Snackbar.make(view, getResources().getString(R.string.snacbar_error_connect), Snackbar.LENGTH_LONG).show();
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
