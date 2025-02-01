package com.example.savemysoul2_0.androidUuidGenerator

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import javax.inject.Inject
import kotlin.random.Random

class AndroidUuidGenerator @Inject constructor(
    private val context: Context
) {
    private val UUID_KEY = "android_guid"

    // Function to get or create a GUID
    fun getOrCreateGuid(): String {
        val storedGuid = getStoredGuid()
        return if (storedGuid != null) {
            storedGuid
        } else {
            val deviceData = getDeviceUniqueData()
            val generatedGuid = hashToGuid(deviceData)
            saveGuid(generatedGuid)
            generatedGuid
        }
    }

    // Get unique device data
    private fun getDeviceUniqueData(): String {
        return getAndroidDeviceId()
    }

    // Get the device identifier
    @SuppressLint("HardwareIds")
    private fun getAndroidDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // Data for fallback mode
    private fun getFallbackData(): String {
        // Generate a random string
        val randomBytes = Random.Default.nextBytes(16)
        return randomBytes.joinToString("") { "%02x".format(it) }
    }

    // Convert device data into a hashed GUID
    private fun hashToGuid(data: String): String {
        val hash = data.hashCode().toString(16).padStart(32, '0')
        return "${hash.substring(0, 8)}-${hash.substring(8, 12)}-${hash.substring(12, 16)}-${hash.substring(16, 20)}-${hash.substring(20, 32)}"
    }

    // Save the GUID to the device
    private fun saveGuid(guid: String) {
        val preferences: SharedPreferences = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
        preferences.edit().putString(UUID_KEY, guid).apply()
    }

    // Get the stored GUID
    private fun getStoredGuid(): String? {
        val preferences: SharedPreferences = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
        return preferences.getString(UUID_KEY, null)
    }
}