package com.example.savemysoul2_0.ui.screens.Home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemysoul2_0.androidUuidGenerator.AndroidUuidGenerator
import com.example.savemysoul2_0.api.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val _androidUuidGenerator: AndroidUuidGenerator,
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    private val _webSocketClient = WebSocketClient()
    val webSocketClient: WebSocketClient get() = _webSocketClient
    val uiState: Flow<HomeUiState> get() = _uiState

    fun setIsLocationSharingStatus(status: Boolean) {
        _uiState.value = _uiState.value.copy(isLocationSharing = status)
    }

    init {
        _webSocketClient.connect("ws:///192.168.1.104:8080/ws/location")
    }

    fun sendSOS(context: Context, latitude: Double, longitude: Double) {
        val uuid = _androidUuidGenerator.getOrCreateGuid()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val json = JSONObject().apply {
                    put("uuid", uuid)
                    put("latitude", latitude)
                    put("longitude", longitude)
                }
                _webSocketClient.sendSOS(json.toString())
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun startLocationSharing(context: Context, locationUtils: LocationUtils) {
        viewModelScope.launch(Dispatchers.IO) {
            locationUtils.startLocationUpdates { location ->
                if (location != null) {
                    sendSOS(context, location.latitude, location.longitude)
                }
            }
        }
    }
}