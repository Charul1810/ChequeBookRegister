package com.incognysis.cheque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charul on 23-12-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cheque_manager";

    // Contacts table name
    private static final String TABLE_CHEQUE = "cheque";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_BANK = "bank";
    private static final String KEY_GIVENTO = "givento";
    private static final String KEY_ENTRY_DATE = "date";
    private static final String KEY_ISSUE_DATE = "date";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CHEQUENO = "chequeno";
    private static final String KEY_STATUS = "status";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_REMINDER = "reminder";


    //private static final String KEY_DATE="created_date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHEQUE_TABLE = "CREATE TABLE " + TABLE_CHEQUE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TYPE + " TEXT,"
                + KEY_BANK + " TEXT,"
                + KEY_GIVENTO + " TEXT,"
                + KEY_ENTRY_DATE + " TEXT,"
                + KEY_ISSUE_DATE + " TEXT,"
                + KEY_AMOUNT + " TEXT,"
                + KEY_CHEQUENO + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_NOTES + " TEXT, "
               + KEY_REMINDER + " TEXT "
                + ")";

        db.execSQL(CREATE_CHEQUE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void add_cheque(cheque c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, c.get_type());
        values.put(KEY_BANK, c.get_bank());
        values.put(KEY_GIVENTO, c.get_givenTo());
        values.put(KEY_ENTRY_DATE, c.get_entry_date());
        values.put(KEY_ISSUE_DATE,c.get_issue_date());
        values.put(KEY_AMOUNT, c.get_amount());
        values.put(KEY_CHEQUENO, c.get_chequeNo());
        values.put(KEY_STATUS, c.get_status());
        values.put(KEY_NOTES, c.get_notes());
       values.put(KEY_REMINDER,c.get_reminder());
        db.insert(TABLE_CHEQUE, null, values);
        db.close();
    }


    // Getting All Contacts
    public List<cheque> getAllCheques() {
        List<cheque> chequeList = new ArrayList<cheque>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHEQUE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                cheque c = new cheque();

                c.setId(Integer.parseInt(cursor.getString(0)));
                c.set_type(cursor.getString(1));
                c.set_bank(cursor.getString(2));
                c.set_givenTo(cursor.getString(3));
                c.set_entry_date(cursor.getString(4));
                c.set_entry_date(cursor.getString(5));
                c.set_amount(cursor.getString(6));
                c.set_chequeNo(cursor.getString(7));
                c.set_status(cursor.getString(8));
                c.set_status(cursor.getString(9));
                c.set_notes(cursor.getString(10));
               c.set_reminder(cursor.getString(11));


                // Adding contact to list
                chequeList.add(c);
            } while (cursor.moveToNext());
        }

        // return note list
        return chequeList;
    }

/*
    // Updating single contact
    public int updateNote(note n) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, n.get_title());
        values.put(KEY_NOTE, n.get_note());
        values.put(KEY_DAY, n.get_day());
        values.put(KEY_TIME, n.get_reminder_time());
        //************************
//        values.put(KEY_DATE, n.get_created().getTime());
        //************************


        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(n.get_id())});
    }


    // Deleting single contact
    public void deleteNote(note n) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(n.get_id())});
        db.close();
    }

*/
}
