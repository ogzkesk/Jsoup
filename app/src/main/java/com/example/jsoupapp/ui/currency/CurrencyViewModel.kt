package com.example.jsoupapp.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsoupapp.data.remote.Api
import com.example.jsoupapp.data.remote.Euro
import com.example.jsoupapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

    init {
        checkEuro()
    }

    private val _observeEuro = MutableStateFlow<UiState<Euro>?>(null)
    val observeEuro = _observeEuro.asStateFlow()

    fun checkEuro() = viewModelScope.launch(Dispatchers.IO) {
        println("checkEuro Called")
        _observeEuro.value = UiState.Loading()
        val response = api.checkEuro()
        response?.let {
            _observeEuro.value = UiState.Success(it)
            return@launch
        }
        _observeEuro.value = UiState.Error("null")

    }
}