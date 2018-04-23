package kz.sgq.fs_imaytber.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.sgq.fs_imaytber.R;
import kz.sgq.fs_imaytber.ui.adapters.LoginPageAdapter;
import kz.sgq.fs_imaytber.ui.fragment.LoginFragment;
import kz.sgq.fs_imaytber.ui.fragment.SignupFragment;
import kz.sgq.fs_imaytber.util.interfaces.OnSelectedButtonListener;

public class LoginActivity extends AppCompatActivity implements OnSelectedButtonListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private LoginFragment loginFragment;
    private SignupFragment signupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupViewPager();
    }

    private void setupViewPager() {
        LoginPageAdapter adapter = new LoginPageAdapter(getSupportFragmentManager());
        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
        adapter.addFragmetn(loginFragment);
        adapter.addFragmetn(signupFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClickLogin() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onClickSignup() {
        viewPager.setCurrentItem(0);
    }
}
