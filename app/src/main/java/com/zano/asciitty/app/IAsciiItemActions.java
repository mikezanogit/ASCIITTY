package com.zano.asciitty.app;

/**
 * Created by mamanzan on 5/21/2014.
 */
public interface IAsciiItemActions {
    public void editAsciiItem(AsciiArtItem item);
    public void deleteAsciiItem(AsciiArtItem item);
    public void saveAsciiItem(AsciiArtItem item);
    public void cancelAsciiItem();
    public void createAsciiItem();
}
