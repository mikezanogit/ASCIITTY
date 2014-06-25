package com.zano.asciitty.app;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mamanzan on 6/3/2014.
 * Using this page to try to start app with a default database
 * http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */
public class SQLite extends SQLiteOpenHelper {




    public static final String TABLE_ASCII_ART = "ascii_art";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATA = "data";

    public static final String DATABASE_PATH = "/data/data/com.zano.asciitty.app/databases/";
    private static final String DATABASE_NAME = "ascii_art.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_ASCII_ART + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DATA + " text not null );";


    private final Context mContext;

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    /**
     * Check if the database actually exists.
     * @return False when the database does not exist or if there is an error.
     */
    private boolean checkDatabase() {

        SQLiteDatabase checkDB;
        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.
            return false;
            //return null;
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    /**
     * Create the database (if necessary)
     */
    public void createDatabase() {
        boolean dbExist = this.checkDatabase();
        //SQLiteDatabase instance = this.checkDatabase();

        if(!dbExist){

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();


            try {

                copyDataBase();
                //very important to close the helper here
                close();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

        //return this.getWritableDatabase();
        //return instance;
    }
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        String dir = mContext.getApplicationInfo().dataDir + "/databases";


        AssetManager am = mContext.getAssets();
        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

}
