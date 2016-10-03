package nes.com.elephanote.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;


public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sqlite_database";
    public static final String TABLE_NAME = "note_list";
    private static Database instance = null;
    private static String note = "note";
    private static String date = "date";
    private static String id = "id";
    private static String category = "category";

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + date + " TEXT,"
                + note + " TEXT,"
                + category + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    /* TO DO
    public void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, this.id + " =?", new String[]{String.valueOf(id)});
    }*/
    public void addNote(String note, Date date, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.date, date.getTime());
        values.put(this.note, note);
        values.put(this.category, category);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "date", "note", "category"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getString(2));
            note.setId(cursor.getInt(0));
            note.setDate(new Date(cursor.getLong(1)));
            note.setCategory(Integer.parseInt(cursor.getString(3)));
            notes.add(note);
        }

        return notes;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
