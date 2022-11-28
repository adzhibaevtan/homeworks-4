package com.task.homework_4.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.task.homework_4.data.local.preferences.Constants.HAS_USER_SEEN_ON_BOARDING

class PreferencesManager(private val sharedPreferences: SharedPreferences) {

    var hasUserSeenOnBoarding: Boolean
        get() = sharedPreferences.getBoolean(HAS_USER_SEEN_ON_BOARDING, false)
        set(value) = sharedPreferences.put(HAS_USER_SEEN_ON_BOARDING, value)

}

fun SharedPreferences.put(key: String, value: Any) {
    edit {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
        }
    }
}