package com.lanji.mylibrary.inject;

import android.content.Context;
import android.view.animation.AnimationUtils;

public class ResLoader {

    public static Object loadRes(ResType type, Context context, int id) {
        if (context == null || id < 1) return null;
        switch (type) {
            case Animation:
                return AnimationUtils.loadAnimation(context, id);
            case Boolean:
                return context.getResources().getBoolean(id);
            case Color:
                return context.getResources().getColor(id);
            case ColorStateList:
                return context.getResources().getColorStateList(id);
            case Dimension:
                return context.getResources().getDimension(id);
            case DimensionPixelOffset:
                return context.getResources().getDimensionPixelOffset(id);
            case DimensionPixelSize:
                return context.getResources().getDimensionPixelSize(id);
            case Drawable:
                return context.getResources().getDrawable(id);
            case Integer:
                return context.getResources().getInteger(id);
            case IntArray:
                return context.getResources().getIntArray(id);
            case Movie:
                return context.getResources().getMovie(id);
            case String:
                return context.getResources().getString(id);
            case StringArray:
                return context.getResources().getStringArray(id);
            case Text:
                return context.getResources().getText(id);
            case TextArray:
                return context.getResources().getTextArray(id);
            case Xml:
                return context.getResources().getXml(id);
            default:
                break;
        }

        return null;
    }
}
