package com.zano.asciitty.app;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.View;

/**
 * Created by mamanzan on 6/25/2014.
 */
public class FastInputIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    @Override
    public View onCreateInputView() {

        MyKeyboardView inputView =
                (MyKeyboardView) getLayoutInflater().inflate(R.layout.keyboard_ascii_items, null);

        inputView.setOnKeyboardActionListener(this);
        //inputView.setKeyboard();
        //getLayoutInflater().inflate(R.layout.meth)
        return super.onCreateInputView();
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
