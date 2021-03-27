package com.example.puzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tabelaWynikow";
    private static final String TABLE_WYNIKI = "wyniki";
    private static final String KEY_ID = "id";
    private static final String KEY_NICK = "nick";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WYNIKI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NICK + " TEXT,"
                + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WYNIKI);

        // Create tables again
        onCreate(db);
    }
    // code to add the new wynik
    void addWynik(Wyniki wynik) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NICK, wynik.getNickName()); // Wynik Nick
        values.put(KEY_TIME, (String) wynik.getTime()); // Wynik Time

        // Inserting Row
        db.insert(TABLE_WYNIKI, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all wyniki in a list view
    public List<Wyniki> getAllWyniki() {
        List<Wyniki> wynikiList = new ArrayList<Wyniki>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WYNIKI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Wyniki wyniki = new Wyniki();
                wyniki.setId(Integer.parseInt(cursor.getString(0)));
                wyniki.setNickName(cursor.getString(1));
                wyniki.setTime(cursor.getString(2));
                // Adding wynik to list
                wynikiList.add(wyniki);
            } while (cursor.moveToNext());
        }
        // return wynik list
        return wynikiList;
    }

    // Deleting single wynik
    public void deleteContact(Wyniki wyniki) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WYNIKI, KEY_ID + " = ?",
                new String[] { String.valueOf(wyniki.getId()) });
        db.close();
    }





}
