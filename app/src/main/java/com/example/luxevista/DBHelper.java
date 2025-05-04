package com.example.luxevista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
                "stay_type TEXT," +
                "stay_availability BOOLEAN" + ");";

        String populateStays = "INSERT INTO stays (stay_id, stay_type, stay_availability) VALUES " +
                "(101, 'RS', 1), (102, 'RS', 1), (103, 'RS', 1), (104, 'RS', 1), (105, 'RS', 1), " +
                "(106, 'RS', 1), (107, 'RS', 1), (108, 'RS', 1), (109, 'RS', 1), " +
                "(201, 'RS', 1), (202, 'RS', 1), (203, 'RS', 1), (204, 'RS', 1), (205, 'RS', 1), " +
                "(206, 'RS', 1), (207, 'RS', 1), (208, 'RS', 1), (209, 'RS', 1), " +
                "(301, 'FF', 1), (302, 'FF', 1), (303, 'FF', 1), (304, 'FF', 1), (305, 'FF', 1), " +
                "(306, 'FF', 1), (307, 'FF', 1), (308, 'FF', 1), (309, 'FF', 1), " +
                "(401, 'PL', 1), (402, 'PL', 1), (403, 'PL', 1), (404, 'PL', 1), (405, 'PL', 1), " +
                "(406, 'PL', 1), (407, 'PL', 1), (408, 'PL', 1), (409, 'PL', 1), " +
                "(501, 'PL', 1), (502, 'PL', 1), (503, 'PL', 1), (504, 'PL', 1), (505, 'PL', 1), " +
                "(506, 'PL', 1), (507, 'PL', 1), (508, 'PL', 1), (509, 'PL', 1), " +
                "(601, 'SG', 1), (602, 'SG', 1), (603, 'SG', 1), (604, 'SG', 1), (605, 'SG', 1), " +
                "(606, 'SG', 1), (607, 'SG', 1), (608, 'SG', 1), (609, 'SG', 1), " +
                "(701, 'SG', 1), (702, 'SG', 1), (703, 'SG', 1), (704, 'SG', 1), (705, 'SG', 1), " +
                "(706, 'SG', 1), (707, 'SG', 1), (708, 'SG', 1), (709, 'SG', 1), " +
                "(801, 'D', 1), (802, 'D', 1), (803, 'D', 1), (804, 'D', 1), (805, 'D', 1), " +
                "(806, 'D', 1), (807, 'D', 1), (808, 'D', 1), (809, 'D', 1), " +
                "(901, 'D', 1), (902, 'D', 1), (903, 'D', 1), (904, 'D', 1), (905, 'D', 1), " +
                "(906, 'D', 1), (907, 'D', 1), (908, 'D', 1), (909, 'D', 1);";

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

    public List<Integer> getAvailableRooms(String stayTypeCode) {
        List<Integer> availableRooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT stay_id FROM stays WHERE stay_type = ? AND stay_availability = 1";
        Cursor cursor = db.rawQuery(query, new String[]{stayTypeCode});

        if (cursor.moveToFirst()) {
            do {
                int stayId = cursor.getInt(cursor.getColumnIndexOrThrow("stay_id"));
                availableRooms.add(stayId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return availableRooms;
    }

}
