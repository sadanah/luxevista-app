package com.example.luxevista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    private Context context;
    private static final String DATABASE_NAME = "luxevista.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRegistrationsTable = "CREATE TABLE registrations (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "phone TEXT," +
                "username TEXT," +
                "password TEXT," +
                "role TEXT," +
                "login_id INTEGER," +
                "FOREIGN KEY(login_id) REFERENCES logins(login_id)" + ");";

        String createLoginsTable = "CREATE TABLE logins (" +
                "login_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "role TEXT" + ");";

        String createStaysTable = "CREATE TABLE stays (" +
                "stay_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "stay_type INTEGER," +
                "stay_price INTEGER," +
                "stay_availability BOOLEAN" + ");";

        String createPaymentsTable = "CREATE TABLE payments (" +
                "pay_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pay_amount REAL," +
                "pay_provider TEXT," +
                "timestamp TEXT" + ");";

        String createBookingsTable = "CREATE TABLE bookings (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "stay_id INTEGER," +
                "pay_id INTEGER," +
                "check_in TEXT," +
                "check_out TEXT," +
                "person_count INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES registrations(user_id)," +
                "FOREIGN KEY(stay_id) REFERENCES stays(stay_id)," +
                "FOREIGN KEY(pay_id) REFERENCES payments(pay_id)" +
                ");";


        db.execSQL(createRegistrationsTable);
        db.execSQL(createLoginsTable);
        db.execSQL(createStaysTable);
        db.execSQL(createPaymentsTable);
        db.execSQL(createBookingsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bookings");
        db.execSQL("DROP TABLE IF EXISTS payments");
        db.execSQL("DROP TABLE IF EXISTS stays");
        db.execSQL("DROP TABLE IF EXISTS registrations");
        db.execSQL("DROP TABLE IF EXISTS logins");
        onCreate(db);
    }


    void addRegistration(String email, String phone, String username, String password, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("email", email);
        cv.put("phone", phone);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role);
        long result = db.insert("registrations", null, cv);
        if(result == -1){
            Toast.makeText(context, "DB: Add Registration Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Registration Success", Toast.LENGTH_SHORT).show();
        }
    }

    void addLogin(String username, String password, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role);
        long result = db.insert("logins", null, cv);  // Fixed table name
        if(result == -1){
            Toast.makeText(context, "DB: Add Login Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Login Success", Toast.LENGTH_SHORT).show();
        }
    }

    void addStay(int stayType, int stayPrice, boolean stayAvailability){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("stay_type", stayType);
        cv.put("stay_price", stayPrice);
        cv.put("stay_availability", stayAvailability ? 1 : 0); // SQLite uses 0 and 1 for BOOLEAN
        long result = db.insert("stays", null, cv);
        if(result == -1){
            Toast.makeText(context, "DB: Add Stay Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Stay Success", Toast.LENGTH_SHORT).show();
        }
    }

    void addPayment(double payAmount, String payProvider, String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("pay_amount", payAmount);
        cv.put("pay_provider", payProvider);
        cv.put("timestamp", timestamp); // e.g., "2025-04-29 14:30:00"
        long result = db.insert("payments", null, cv);
        if(result == -1){
            Toast.makeText(context, "DB: Add Payment Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Payment Success", Toast.LENGTH_SHORT).show();
        }
    }

    void addBooking(int userId, int stayId, int payId, String checkIn, String checkOut, int personCount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", userId);
        cv.put("stay_id", stayId);
        cv.put("pay_id", payId);
        cv.put("check_in", checkIn);   // e.g., "2025-05-01"
        cv.put("check_out", checkOut); // e.g., "2025-05-05"
        cv.put("person_count", personCount);
        long result = db.insert("bookings", null, cv);
        if(result == -1){
            Toast.makeText(context, "DB: Add Booking Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Booking Success", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM logins WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username, password});

        boolean result = false;
        if (cursor.moveToFirst()) {
            // Found a matching login
            result = true;
        }
        cursor.close();
        return result;
    }

}
