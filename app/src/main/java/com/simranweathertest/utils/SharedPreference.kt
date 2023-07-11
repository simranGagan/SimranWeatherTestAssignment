package com.simranweathertest.utils

import android.content.Context
import android.text.TextUtils


object SharedPreference {
    fun putSharedPreferencesString(context: Context, key: String?, value: String?) {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putString(key, value)
        edit.commit()
    }

    fun getSharedPreferencesString(context: Context, key: String?): String? {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        return if (!TextUtils.isEmpty(preferences.getString(key, ""))) {
            preferences.getString(key, "")
        } else ""
    }

    fun putSharedPreferencesInteger(context: Context, key: String?, value: Int) {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putInt(key, value)
        edit.commit()
    }

    fun getSharedPreferencesInteger(context: Context, key: String?): Int {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        return preferences.getInt(key, 0)
    }

    fun clearsharedPreferences(context: Context) {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.commit()
    }

    fun getSharedPreferencesBoolean(context: Context, key: String?): Boolean {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        return preferences.getBoolean(key, false)
    }

    fun putSharedPreferencesBoolean(context: Context, key: String?, value: Boolean) {
        val preferences =
            context.getSharedPreferences(context.packageName + "_PREFS", Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putBoolean(key, value)
        edit.commit()
    }



}