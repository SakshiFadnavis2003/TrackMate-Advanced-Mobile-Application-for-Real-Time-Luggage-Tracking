package com.example.trackmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    // User table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_CONTACT = "contact_number";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Luggage table
    private static final String TABLE_LUGGAGE = "luggage";
    private static final String COLUMN_LUGGAGE_ID = "id";
    private static final String COLUMN_LUGGAGE_USERNAME = "username"; // Foreign key
    private static final String COLUMN_LUGGAGE_COMPANY = "company";
    private static final String COLUMN_LUGGAGE_COLOR = "color";
    private static final String COLUMN_LUGGAGE_CAPACITY = "capacity";
    private static final String COLUMN_LUGGAGE_HEIGHT = "height";
    private static final String COLUMN_LUGGAGE_WIDTH = "width";

    // Create user table query
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT NOT NULL, " +
                    COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_USER_CONTACT + " TEXT NOT NULL CHECK(LENGTH(" + COLUMN_USER_CONTACT + ") >= 10 AND LENGTH(" + COLUMN_USER_CONTACT + ") <= 15), " +
                    COLUMN_USER_USERNAME + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_USER_PASSWORD + " TEXT NOT NULL CHECK(LENGTH(" + COLUMN_USER_PASSWORD + ") >= 8))";

    // Create luggage table query
    private static final String CREATE_TABLE_LUGGAGE =
            "CREATE TABLE " + TABLE_LUGGAGE + "(" +
                    COLUMN_LUGGAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LUGGAGE_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_LUGGAGE_COMPANY + " TEXT NOT NULL, " +
                    COLUMN_LUGGAGE_COLOR + " TEXT NOT NULL, " +
                    COLUMN_LUGGAGE_CAPACITY + " INTEGER NOT NULL, " +
                    COLUMN_LUGGAGE_HEIGHT + " REAL NOT NULL, " +
                    COLUMN_LUGGAGE_WIDTH + " REAL NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_LUGGAGE_USERNAME + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_USERNAME + "))";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create both tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LUGGAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    // Method to retrieve user data by username
    public UserData getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserData userData = null;
        String[] projection = {
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_CONTACT,
                COLUMN_USER_ID // Include userId field
        };
        String selection = COLUMN_USER_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
            String contact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CONTACT));
            String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)); // Retrieve userId
            userData = new UserData(name, email, contact, userId);
            cursor.close();
        }

        return userData;
    }
}
