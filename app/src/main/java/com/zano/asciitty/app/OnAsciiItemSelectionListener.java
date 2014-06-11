package com.zano.asciitty.app;

/**
 * Created by mamanzan on 5/21/2014.
 */
public interface OnAsciiItemSelectionListener {
    public void onAsciiItemEdit(AsciiArtItem item);
    public void onAsciiItemDelete(AsciiArtItem item);
    public void onAsciiEditorCancel();
    public void onAsciiEditorSave(AsciiArtItem item);
    public void onAsciiCreateNewItem();
}
