package com.zano.asciitty.app;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.animation.Animation;

/**
 * Created by Michael on 6/25/2014.
 */
public class MyKeyboardView extends KeyboardView {

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showWithAnimation(Animation animation) {
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //setVisibility(View.VISIBLE);
            }
        });

        setAnimation(animation);
    }

}
