package com.zano.asciitty.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamanzan on 6/4/2014.
 */
public class AsciiArtDataSource {

    private SQLiteDatabase db;
    private SQLite dbHelper;
    private String[] allColumns = { SQLite.COLUMN_ID, SQLite.COLUMN_NAME, SQLite.COLUMN_DATA};

    public AsciiArtDataSource(Context context) {
        this.dbHelper = new SQLite(context);
    }

    public void open() throws SQLException {
        this.db = this.dbHelper.getWritableDatabase();
    }

    public void close() {
        this.dbHelper.close();
    }

    public AsciiArtItem createAsciiArtItem(String name, String data) {
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, name);
        values.put(SQLite.COLUMN_DATA, data);

        long insertId = this.db.insert(SQLite.TABLE_ASCII_ART, null, values);

        Cursor cursor = this.db.query(SQLite.TABLE_ASCII_ART, allColumns, SQLite.COLUMN_ID + " = " + insertId,null,null,null,null);
        cursor.moveToFirst();
        AsciiArtItem newItem = this.cursorToAsciiArtItem(cursor);
        cursor.close();
        return newItem;
    }

    public void updateAsciiArtItem(AsciiArtItem item){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, item.getName());
        values.put(SQLite.COLUMN_DATA, item.getData());
        long id = item.getId();

        try {
            this.db.update(
                    SQLite.TABLE_ASCII_ART,
                    values,
                    SQLite.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)});

        } catch (Exception e) {
            Log.e("SQL problem", e.getMessage());
        }
    }

    public void deleteAsciiArtItem(AsciiArtItem item) {
        long id = item.getId();
        this.db.delete(SQLite.TABLE_ASCII_ART, SQLite.COLUMN_ID + " = " + id, null);
    }

    public List<AsciiArtItem> getAllAsciiArtItems() {
        List<AsciiArtItem> items = new ArrayList<AsciiArtItem>();

        Cursor cursor = this.db.query(SQLite.TABLE_ASCII_ART, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AsciiArtItem item = this.cursorToAsciiArtItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    //This should be generalized
    private AsciiArtItem  cursorToAsciiArtItem(Cursor cursor) {
        AsciiArtItem item = new AsciiArtItem();
        item.setId(cursor.getLong(0));
        item.setName(cursor.getString(1));
        item.setData(cursor.getString(2));
        return item;
    }
}

