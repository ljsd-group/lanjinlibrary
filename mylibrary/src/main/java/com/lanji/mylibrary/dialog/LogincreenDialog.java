package com.lanji.mylibrary.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.lanji.mylibrary.R;
import com.lanji.mylibrary.interfaces.LoginDataCallBack;
import com.lanji.mylibrary.utils.LogUtils;
import com.lanji.mylibrary.utils.Method;

public class LogincreenDialog extends Dialog implements View.OnClickListener {

    private LoginDataCallBack mLoginDataCallBack;

    public LogincreenDialog(Context context, LoginDataCallBack mLoginDataCallBack) {
        super(context);
        this.mLoginDataCallBack = mLoginDataCallBack;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }


    private View mImageDialogBackground;
    private String policyLink;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        mImageDialogBackground = findViewById(R.id.viewback);
        if (mLoginDataCallBack.getDialogBackground() != 0) {
            mImageDialogBackground.setBackgroundResource(mLoginDataCallBack.getDialogBackground());
        }
        ImageView mImageBack = findViewById(R.id.backimage);
        mImageBack.setColorFilter(Color.WHITE);
        findViewById(R.id.backlayout).setOnClickListener(this);

        TextView mTextTitle = findViewById(R.id.title);
        mTextTitle.setText(Method.getAppName(getContext()));

        View mLayoutApple = findViewById(R.id.layoutapple);
        mLayoutApple.setOnClickListener(this);

        int appleBack = mLoginDataCallBack.getAppleViewBackground();
        if (appleBack != 0) {
            GradientDrawable bg4 = (GradientDrawable) mLayoutApple.getBackground();
            bg4.setColor(ContextCompat.getColor(getContext(), appleBack));
        }

        View mLayoutGoogle = findViewById(R.id.googlelogin);
        mLayoutGoogle.setOnClickListener(this);
        int googleBack = mLoginDataCallBack.getGoogleViewBackground();
        if (googleBack != 0) {
            GradientDrawable bg4 = (GradientDrawable) mLayoutGoogle.getBackground();
            bg4.setColor(ContextCompat.getColor(getContext(), googleBack));
        }

        TextView mTextPolicy = findViewById(R.id.policy);
        String mTips = getContext().getResources().getString(R.string.login_xieyi);
        String mPolicy = getContext().getResources().getString(R.string.privacy_policy);
        SpannableString spannable = new SpannableString(mTips + mPolicy);


        ClickableSpan redClickable = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                policyLink = mLoginDataCallBack.getPolicyLink();
                if (!TextUtils.isEmpty(policyLink)) {
                    Uri uri = Uri.parse(policyLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(getContext(), R.color.color_00a8ff));
                ds.setUnderlineText(false); // 移除下划线
            }
        };
        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, mTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        spannable.setSpan(redClickable, mTips.length(), mTips.length() + mPolicy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_00a8ff)), mTips.length(), mTips.length() + mPolicy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextPolicy.setHighlightColor(ContextCompat.getColor(getContext(), R.color.transparent));
        mTextPolicy.setText(spannable);
        mTextPolicy.setMovementMethod(LinkMovementMethod.getInstance()); // 必须设置才能生效
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backlayout) {
            dismiss();
        } else if (v.getId() == R.id.layoutapple) {
            login("apple");
        } else if (v.getId() == R.id.googlelogin) {
            login("google-oauth2");
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
                            LogUtils.i("Auth0 login onFailure "+e.toString());
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

