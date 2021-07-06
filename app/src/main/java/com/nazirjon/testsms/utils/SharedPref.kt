package com.nazirjon.currencyvalue.utils

import android.content.Context
import android.content.SharedPreferences
class SharedPreference(context: Context) {

    private val PREFS_NAME = "sms_test"
    private val PREFS_TOKEN_VALUE = "token_value"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Метод сохранение Token
    fun saveTokenValue(token_value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(PREFS_TOKEN_VALUE, token_value).apply()
    }

    // Метод получение Token
    fun getTokenValue(): String {
        return sharedPref.getString(PREFS_TOKEN_VALUE, "")!!
    }

    fun deleteToken() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(PREFS_NAME).clear().apply()
    }
}
