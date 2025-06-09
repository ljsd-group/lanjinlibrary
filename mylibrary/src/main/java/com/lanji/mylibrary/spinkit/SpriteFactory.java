package com.lanji.mylibrary.spinkit;


import com.lanji.mylibrary.spinkit.sprite.Sprite;
import com.lanji.mylibrary.spinkit.style.Circle;
import com.lanji.mylibrary.spinkit.style.ThreeBounce;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            default:
                break;
        }
        return sprite;
    }
}
