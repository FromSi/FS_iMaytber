package kz.sgq.fs_imaytber.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
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
    @BindView(R.id.avatar)
    ImageView avatar;

    private final String TAG_PREF = "profile";
    private ProgressDialog loading;
    private LoginPresenter presenter;
    private Dialog dialogAvatar;
    private String urlAvatar;

    private CircleImageView storage;
    private CircleImageView def1;
    private CircleImageView def2;
    private CircleImageView def3;
    private CircleImageView def4;

    private StorageReference ref;
    private Uri selectedImage;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Random random = new Random(System.currentTimeMillis());
        urlAvatar = "def" + (1 + random.nextInt(4));
        ref = FirebaseStorage.getInstance().getReference()
                .child("avatars/" + UUID.randomUUID().toString());
        dialogAvatar = initDialogAvatar(view.getContext());
        presenter = new SignupPresenterImpl(this);
        loading = new ProgressDialog(view.getContext());
        loading.setMessage(getResources().getString(R.string.loading));
    }

    @OnClick({R.id.ok, R.id.change, R.id.avatar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                if (urlAvatar.length() == 4) {
                    loading.show();
                    presenter.handlerClick();
                } else {
                    loading.show();
                    uploadAvatar(selectedImage);
                }
                break;
            case R.id.change:
                OnSelectedButtonListener listener = (OnSelectedButtonListener) getActivity();
                listener.onClickSignup();
                break;
            case R.id.avatar:
                dialogAvatar.show();
                break;
        }
    }

    private void uploadAvatar(Uri uri) {
        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    urlAvatar = String.valueOf(taskSnapshot.getDownloadUrl());
                    presenter.handlerClick();
                })
                .addOnFailureListener(exception -> {
                    loading.dismiss();
                    Snackbar.make(view, getResources().getString(R.string.snacbar_error_upload_image), Snackbar.LENGTH_LONG).show();
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    avatar.setImageURI(null);
                    storage.setImageURI(null);
                    selectedImage = data.getData();
                    avatar.setImageURI(selectedImage);
                    storage.setImageURI(selectedImage);
                    def1.setBorderWidth(0);
                    def2.setBorderWidth(0);
                    def3.setBorderWidth(0);
                    def4.setBorderWidth(0);
                    storage.setBorderWidth((int) getResources().getDimension(R.dimen.border));
                    urlAvatar = "storage";
                }
        }
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
            setAvatar(R.drawable.def1);
            urlAvatar = "def1";
        });

        def2.setOnClickListener(v -> {
            def1.setBorderWidth(0);
            def2.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            def3.setBorderWidth(0);
            def4.setBorderWidth(0);
            storage.setBorderWidth(0);
            setAvatar(R.drawable.def2);
            urlAvatar = "def2";
        });

        def3.setOnClickListener(v -> {
            def1.setBorderWidth(0);
            def2.setBorderWidth(0);
            def3.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            def4.setBorderWidth(0);
            storage.setBorderWidth(0);
            setAvatar(R.drawable.def3);
            urlAvatar = "def3";
        });

        def4.setOnClickListener(v -> {
            def1.setBorderWidth(0);
            def2.setBorderWidth(0);
            def3.setBorderWidth(0);
            def4.setBorderWidth((int) getResources().getDimension(R.dimen.border));
            storage.setBorderWidth(0);
            setAvatar(R.drawable.def4);
            urlAvatar = "def4";
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
                });
        return builder.create();
    }

    private void setAvatar(int id) {
        avatar.setImageDrawable(getResources().getDrawable(id));
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
        return PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString("token", "1010101");
    }

    @Override
    public String getUrlAvatar() {
        return urlAvatar;
    }

    @Override
    public void setupProfile() {
        Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE).edit()
                .putBoolean(TAG_PREF, false)
                .apply();
    }

    @Override
    public void showErrorNick() {
        nick.setError(getResources().getString(R.string.error_nick));
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
        Snackbar.make(view, getResources().getString(R.string.snacbar_error_create),
                Snackbar.LENGTH_LONG).show();
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
