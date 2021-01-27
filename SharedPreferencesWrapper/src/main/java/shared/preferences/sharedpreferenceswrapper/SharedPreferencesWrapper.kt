package shared.preferences.sharedpreferenceswrapper

import android.content.SharedPreferences

class SharedPreferencesWrapper(sharedPreferences: SharedPreferences) {

    private val defSharedPreferences = sharedPreferences

    private val changeListCallback = HashMap<String, ()->(Unit)>()

    fun putValue(key: String, value: Int) {
        defSharedPreferences.edit().putInt(key, value).apply()
    }

    fun putValue(key: String, value: String) {
        defSharedPreferences.edit().putString(key, value).apply()
    }

    fun putValue(key: String, value: Boolean) {
        defSharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun putValue(key: String, value: Float) {
        defSharedPreferences.edit().putFloat(key, value).apply()
    }

    fun putValue(key: String, value: Set<String>) {
        defSharedPreferences.edit().putStringSet(key, value).apply()
    }

    fun putValue(key: String, value: Long) {
        defSharedPreferences.edit().putLong(key, value).apply()
    }

    fun getValue(key: String, defValue: Int): Int {
        return defSharedPreferences.getInt(key, defValue)
    }

    fun getValue(key: String, defValue: Long): Long {
        return defSharedPreferences.getLong(key, defValue)
    }

    fun getValue(key: String, defValue: String?): String? {
        return defSharedPreferences.getString(key, defValue)
    }

    fun getValue(key: String, defValue: Boolean): Boolean {
        return defSharedPreferences.getBoolean(key, defValue)
    }

    fun getValue(key: String, defValue: Float): Float {
        return defSharedPreferences.getFloat(key, defValue)
    }

    fun getValue(key: String, defValue: Set<String>?): Set<String>? {
        return defSharedPreferences.getStringSet(key, defValue)
    }

    fun removeKey(key: String) {
        defSharedPreferences.edit().remove(key).apply()
    }

    fun registerToKeyChanges(key: String, callbacks: () ->(Unit)) {
        changeListCallback[key] = callbacks
        if (changeListCallback.size == 1) {
            defSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesChangeListener)
        }
    }

    fun unregisterToKeyChanges(key: String, callbacks: () ->(Unit)) {
        changeListCallback.remove(key)
        if (changeListCallback.size == 0) {
            defSharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesChangeListener)
        }
    }

    private val sharedPreferencesChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            key?.let {
                changeListCallback[key]?.let { it() }
            }
        }
}
