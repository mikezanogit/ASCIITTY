package com.zano.asciitty.app;

/**
 * Created by mamanzan on 5/21/2014.
 */
public interface OnAsciiItemSelectionListener {
    public void onAsciiItemSelected(Item item);
    public void onAsciiEditorCancel();
    public void onAsciiEditorSave(AsciiArtItem item);
}
