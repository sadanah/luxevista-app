package com.example.luxevista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "luxevista.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Enable foreign keys
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createRegistrationsTable = "CREATE TABLE registrations (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "role TEXT NOT NULL" +
                ");";

        String createStaysTable = "CREATE TABLE stays (" +
                "stay_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "stay_type TEXT NOT NULL," +
                "stay_availability INTEGER NOT NULL DEFAULT 1" + ");";

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

        String populateStays = "INSERT INTO stays (stay_id, stay_type, stay_availability) VALUES " +
                "(101, 'RS', 1), (102, 'RS', 1), (103, 'RS', 1);";

        db.execSQL(createRegistrationsTable);
        db.execSQL(createStaysTable);
        db.execSQL(populateStays);
        db.execSQL(createPaymentsTable);
        db.execSQL(createBookingsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bookings");
        db.execSQL("DROP TABLE IF EXISTS payments");
        db.execSQL("DROP TABLE IF EXISTS stays");
        db.execSQL("DROP TABLE IF EXISTS registrations");
        onCreate(db);
    }

    // Fixed to include login_id
    void addRegistration(String email, String phone, String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("email", email);
        cv.put("phone", phone);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role);
        long result = db.insert("registrations", null, cv);
        if (result == -1) {
            Toast.makeText(context, "DB: Add Registration Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Registration Success", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getUserData(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM registrations WHERE username=? AND password=?", new String[]{username, password});
    }

    void addStay(String stayType, boolean stayAvailability) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("stay_type", stayType);
        cv.put("stay_availability", stayAvailability ? 1 : 0);
        long result = db.insert("stays", null, cv);
        if (result == -1) {
            Toast.makeText(context, "DB: Add Stay Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Stay Success", Toast.LENGTH_SHORT).show();
        }
    }

    public int addPayment(double payAmount, String payProvider) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("pay_amount", payAmount);
        cv.put("pay_provider", payProvider);
        cv.put("timestamp", String.valueOf(System.currentTimeMillis())); // Store as String (timestamp)

        long result = db.insert("payments", null, cv);

        if (result == -1) {
            Toast.makeText(context, "DB: Add Payment Failed", Toast.LENGTH_SHORT).show();
            return -1;
        } else {
            Toast.makeText(context, "DB: Payment Recorded", Toast.LENGTH_SHORT).show();
            return (int) result; // return generated pay_id
        }
    }

    public void updateStayAvailability(int stayId, boolean isAvailable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("stay_availability", isAvailable ? 1 : 0);

        int rows = db.update("stays", cv, "stay_id = ?", new String[]{String.valueOf(stayId)});

        if (rows > 0) {
            Toast.makeText(context, "DB: Stay availability updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Stay availability update failed", Toast.LENGTH_SHORT).show();
        }
    }

    void addBooking(int userId, int stayId, int payId, String checkIn, String checkOut, int personCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", userId);
        cv.put("stay_id", stayId);
        cv.put("pay_id", payId);
        cv.put("check_in", checkIn);
        cv.put("check_out", checkOut);
        cv.put("person_count", personCount);
        long result = db.insert("bookings", null, cv);
        if (result == -1) {
            Toast.makeText(context, "DB: Add Booking Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DB: Add Booking Success", Toast.LENGTH_SHORT).show();
        }
    }

    public int validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT user_id FROM registrations WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        int userId = -1; // default: invalid login
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
        }
        cursor.close();
        return userId; // returns user_id if valid, or -1 if invalid
    }


    public List<Integer> getAvailableRooms(String stayType) {
        List<Integer> availableRooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT stay_id FROM stays WHERE stay_type = ? AND stay_availability = 1", new String[]{stayType});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int stayId = cursor.getInt(cursor.getColumnIndexOrThrow("stay_id"));
                    availableRooms.add(stayId);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.e("DBHelper", "Cursor is null, no data found for the stayType: " + stayType);
        }
        return availableRooms;
    }

    public Cursor getUserBookings(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM bookings WHERE user_id = ?", new String[]{String.valueOf(userId)});
    }

}
