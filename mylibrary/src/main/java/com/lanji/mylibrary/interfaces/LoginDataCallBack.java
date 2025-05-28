package com.lanji.mylibrary.interfaces;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

public interface LoginDataCallBack {
    @DrawableRes
    default int getDialogBackground() {
        return 0;
    }

    @ColorInt
    default int getAppleViewBackground() {
        return 0;
    }

    @ColorInt
    default int getGoogleViewBackground() {
        return 0;
    }

    String getPolicyLink();

    void onSuccess(String accToken);
}
