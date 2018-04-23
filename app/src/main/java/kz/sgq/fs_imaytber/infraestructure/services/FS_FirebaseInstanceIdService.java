package kz.sgq.fs_imaytber.infraestructure.services;

import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FS_FirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString("token", FirebaseInstanceId.getInstance().getToken())
                .apply();
    }
}
