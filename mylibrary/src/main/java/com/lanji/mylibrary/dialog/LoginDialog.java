package com.lanji.mylibrary.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.lanji.mylibrary.R;
import com.lanji.mylibrary.interfaces.LoginCallBack;
import com.lanji.mylibrary.interfaces.LoginDataCallBack;
import com.lanji.mylibrary.utils.LogUtils;

public class LoginDialog extends Dialog implements View.OnClickListener {

    private final LoginCallBack mLoginDataCallBack;
    private final int layoutId;
    private String policyLink;

    public LoginDialog(Context context, int layoutId, LoginCallBack mLoginDataCallBack) {
        super(context);
        this.layoutId = layoutId;
        this.mLoginDataCallBack = mLoginDataCallBack;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    public void setPolicyLink(String policyLink) {
        this.policyLink = policyLink;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        View v = findViewById(R.id.login_cancle);
        if (v != null) {
            v.setOnClickListener(this);
        }
        View logingoogle = findViewById(R.id.login_google);
        if (logingoogle != null) {
            logingoogle.setOnClickListener(this);
        }
        View loginapple = findViewById(R.id.login_apple);
        if (loginapple != null) {
            loginapple.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_cancle) {
            dismiss();
        } else if (v.getId() == R.id.login_policy_url) {
            if (!TextUtils.isEmpty(policyLink)) {
                Uri uri = Uri.parse(policyLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        } else if (v.getId() == R.id.login_apple) {
            login("apple");
        } else if (v.getId() == R.id.login_google) {
            login("google-oauth2");
        } else {
            LogUtils.v("login ids  not found");
        }
    }

    private void login(String type) {

        try {
            ApplicationInfo actInfo = getContext().getPackageManager().getApplicationInfo(
                    getContext().getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = actInfo.metaData;
            String domain = bundle.getString("AUTH0_DOMAIN");
            String clientId = bundle.getString("AUTH0_CLIENTID");
            String scheme = bundle.getString("AUTH0_SCHEME");
            LogUtils.i("auth0: scheme=" + scheme + " domain=" + domain + ",clientId=" + clientId);

            if (TextUtils.isEmpty(domain) || TextUtils.isEmpty(clientId) || TextUtils.isEmpty(scheme)) {

                return;
            }
            Auth0 account = Auth0.getInstance(clientId, domain);
            WebAuthProvider.login(account).withScheme(scheme)
                    .withConnection(type)
                    .start(getContext(), new Callback<Credentials, AuthenticationException>() {
                        @Override
                        public void onSuccess(Credentials credentials) {
                            LogUtils.i("Auth0 login onSuccess：" + credentials.getAccessToken());
                            mLoginDataCallBack.onSuccess(credentials.getAccessToken());
                        }

                        @Override
                        public void onFailure(@NonNull AuthenticationException e) {
                            LogUtils.i("Auth0 login onFailure " + e.toString());
                        }
                    });
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStart() {
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}

