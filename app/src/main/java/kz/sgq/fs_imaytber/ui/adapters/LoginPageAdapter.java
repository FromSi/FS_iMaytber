package kz.sgq.fs_imaytber.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoginPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();

    public LoginPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmetn(Fragment fragment) {
        fragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
