package kz.sgq.fs_imaytber.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kz.sgq.fs_imaytber.R;

public class BaseActivity extends AppCompatActivity {


    private SharedPreferences preferences;
    private final String TAG_PREF = "profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        preferences = getSharedPreferences("local", MODE_PRIVATE);
        init();
    }

    private void init() {
        if (preferences.getBoolean(TAG_PREF, false)) {
            Log.d("TagTest", "Yes");
            start(new Intent(this, MainActivity.class));
        } else {
            preferences.edit()
                    .putBoolean(TAG_PREF, false)
                    .apply();
            Log.d("TagTest", "No");
            start(new Intent(this, LoginActivity.class));
        }
    }

    private void start(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
