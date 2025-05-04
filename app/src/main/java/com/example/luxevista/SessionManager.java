package com.example.luxevista;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";

    // Keys
    private static final String KEY_LOGIN_ID = "login_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String IS_LOGGED_IN = "is_logged_in";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ✅ Start user session (Save user info)
    public void createLoginSession(int loginId, String username, String role) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(KEY_LOGIN_ID, loginId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    // ✅ Getters
    public int getLoginId() {
        return sharedPreferences.getInt(KEY_LOGIN_ID, -1);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    // ✅ Check if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    // ✅ Logout user (clear session)
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}
