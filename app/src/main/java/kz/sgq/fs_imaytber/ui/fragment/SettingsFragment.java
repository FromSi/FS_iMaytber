package kz.sgq.fs_imaytber.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
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
    @BindView(R.id.avatar)
    CircleImageView avatar;

    private SettingsPresenter presenter;
    private SharedPreferences preferences;
    private Dialog dialogAvatar;

    private String baseURL;
    private String avatarURL;

    private CircleImageView storage;
    private CircleImageView def1;
    private CircleImageView def2;
    private CircleImageView def3;
    private CircleImageView def4;

    private StorageReference ref;
    private Uri selectedImage;

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
        dialogAvatar = initDialogAvatar(getContext());
        init();
    }

    private void init() {
        ref = FirebaseStorage.getInstance().getReference()
                .child("avatars/" + UUID.randomUUID().toString());
        if (preferences.getBoolean("main_fragment", true))
            radioDialogs.setChecked(true);
        else
            radioFriends.setChecked(true);
    }

    @OnClick({R.id.save, R.id.avatar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                if (nick.length() >= 5)
                    presenter.onClickNick(nick.getText().toString());
                if (password.length() >= 5)
                    presenter.onClickPassword(password.getText().toString());
                break;
            case R.id.avatar:
                dialogAvatar.show();
                break;
        }
    }

    @OnCheckedChanged({R.id.radioDialogs, R.id.radioFriends})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if (checked) {
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
    public void setAvatar(String url) {
        switch (url) {
            case "def1":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def1));
                def1.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                avatarURL = "def1";
                break;
            case "def2":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def2));
                def2.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                avatarURL = "def2";
                break;
            case "def3":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def3));
                def3.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                avatarURL = "def3";
                break;
            case "def4":
                avatar.setImageDrawable(getResources()
                        .getDrawable(R.drawable.def4));
                def4.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                avatarURL = "def4";
                break;
            default:
                Picasso.get()
                        .load(url)
                        .into(avatar);
                Picasso.get()
                        .load(url)
                        .into(storage);
                avatarURL = url;
                storage.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                break;
        }
        baseURL = avatarURL;
    }

    @Override
    public String getUrlAvatar() {
        return baseURL;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ExitAndDestroy", this.getClass().getName());
        presenter.onDestroy();
    }

    private Dialog initDialogAvatar(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = getLayoutInflater().inflate(R.layout.dialog_avatar, null);
        def1 = view.findViewById(R.id.def1);
        def1.setBorderColor(getResources().getColor(R.color.colorBorderImg));
        def2 = view.findViewById(R.id.def2);
        def2.setBorderColor(getResources().getColor(R.color.colorBorderImg));
        def3 = view.findViewById(R.id.def3);
        def3.setBorderColor(getResources().getColor(R.color.colorBorderImg));
        def4 = view.findViewById(R.id.def4);
        def4.setBorderColor(getResources().getColor(R.color.colorBorderImg));
        storage = view.findViewById(R.id.storage);
        storage.setBorderColor(getResources().getColor(R.color.colorBorderImg));

        def1.setOnClickListener(v -> {
            def1.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            def2.setBorderWidth(0);
            def3.setBorderWidth(0);
            def4.setBorderWidth(0);
            storage.setBorderWidth(0);
//            setAvatar(R.drawable.def1);
            baseURL = "def1";
            Log.d("TestTagSize", String.valueOf("def1".length()));
        });

        def2.setOnClickListener(v -> {
            def1.setBorderWidth(0);
            def2.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            def3.setBorderWidth(0);
            def4.setBorderWidth(0);
            storage.setBorderWidth(0);
//            setAvatar(R.drawable.def2);
            baseURL = "def2";
        });

        def3.setOnClickListener(v -> {
            def1.setBorderWidth(0);
            def2.setBorderWidth(0);
            def3.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            def4.setBorderWidth(0);
            storage.setBorderWidth(0);
//            setAvatar(R.drawable.def3);
            baseURL = "def3";
        });

        def4.setOnClickListener(v -> {
            def1.setBorderWidth(0);
            def2.setBorderWidth(0);
            def3.setBorderWidth(0);
            def4.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            storage.setBorderWidth(0);
//            setAvatar(R.drawable.def4);
            baseURL = "def4";
        });

        storage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Avatar"), 1);
        });

        builder.setView(view)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    if (baseURL.equals(avatarURL)){
                        setAvatar(avatarURL);
                    } else {
                        if (baseURL.length() == 4) {
                            presenter.handlerAvatar(baseURL);
                        } else {
                            uploadAvatar(selectedImage);
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    storage.setImageURI(null);
                    selectedImage = data.getData();
                    storage.setImageURI(selectedImage);
                    def1.setBorderWidth(0);
                    def2.setBorderWidth(0);
                    def3.setBorderWidth(0);
                    def4.setBorderWidth(0);
                    storage.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                    baseURL = "storage";
                }
        }
    }

    private void uploadAvatar(Uri uri) {
        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    baseURL = String.valueOf(taskSnapshot.getDownloadUrl());
                    presenter.handlerAvatar();
                })
                .addOnFailureListener(exception -> {
                });
    }
}
