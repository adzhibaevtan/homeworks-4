package com.task.homework_4.data.local.preferences

import android.content.SharedPreferences
import com.task.homework_4.data.local.preferences.Constants.AGE
import com.task.homework_4.data.local.preferences.Constants.HAS_USER_SEEN_ON_BOARDING
import com.task.homework_4.data.local.preferences.Constants.NAME
import com.task.homework_4.data.local.preferences.Constants.PROFILE_IMAGE_PATH

class PreferencesManager(private val sharedPreferences: SharedPreferences) {

    var hasUserSeenOnBoarding: Boolean
        get() = sharedPreferences.getBoolean(HAS_USER_SEEN_ON_BOARDING, false)
        set(value) = sharedPreferences.put(HAS_USER_SEEN_ON_BOARDING, value)

    var profileImagePath: String?
        get() = sharedPreferences.getString(PROFILE_IMAGE_PATH, "")
        set(value) = sharedPreferences.put(PROFILE_IMAGE_PATH, value)

    var name: String?
        get() = sharedPreferences.getString(NAME, "")
        set(value) = sharedPreferences.put(NAME, value)

    var age: String?
        get() = sharedPreferences.getString(AGE, "")
        set(value) = sharedPreferences.put(AGE, value)
}