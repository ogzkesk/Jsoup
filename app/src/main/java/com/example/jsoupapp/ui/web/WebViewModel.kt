package com.example.jsoupapp.ui.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsoupapp.data.remote.Api
import com.example.jsoupapp.data.remote.Source
import com.example.jsoupapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _searchResult = MutableStateFlow<UiState<List<Source>>?>(null)
    val searchResult = _searchResult.asStateFlow()

    fun searchOnUnsplash(query: String){
        _searchResult.value = UiState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = api.searchOnUnsplash(query)
            if(response.isNotEmpty()){
                _searchResult.value = UiState.Success(response)
                return@launch
            }
            _searchResult.value = UiState.Error("Empty")
        }
    }


}