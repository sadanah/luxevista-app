package com.example.luxevista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";

    // Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String IS_LOGGED_IN = "is_logged_in";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ✅ Save user_id and login state
    public void saveSession(int userId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // ✅ Get user_id of logged-in user
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    // ✅ Check if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    // ✅ Logout user (clear session)
    public void logoutUser() {
        editor.clear();
        editor.commit();

        // After logout, redirect to Login screen
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
